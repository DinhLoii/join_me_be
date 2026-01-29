package com.join.me.modules.auth.service.impl;

import com.join.me.common.constants.JwtClaimSetConstant;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.auth.dto.request.AuthenticationRequest;
import com.join.me.modules.auth.dto.request.IntrospectRequest;
import com.join.me.modules.auth.dto.request.RegisterRequest;
import com.join.me.modules.auth.dto.response.AuthenticationResponse;
import com.join.me.modules.auth.dto.response.IntrospectResponse;
import com.join.me.modules.auth.service.api.AuthenticationService;
import com.join.me.modules.auth.service.api.EmailVerificationService;
import com.join.me.modules.mail.service.api.MailService;
import com.join.me.modules.profile.service.api.ProfileService;
import com.join.me.modules.user.entity.Role;
import com.join.me.modules.user.entity.User;
import com.join.me.modules.user.enums.UserStatus;
import com.join.me.modules.user.constants.role.PredefinedRole;
import com.join.me.modules.user.repository.RoleRepository;
import com.join.me.modules.user.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    EmailVerificationService emailVerificationService;
    MailService mailService;
    ProfileService profileService;

    @NonFinal
    @Value(value = "${security.jwt.signer-key}")
    String SIGNER_KEY;

    // authenticate
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String login = request.getUsername();

        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .orElseThrow(() -> new AppException(ErrorCode.USER_USERNAME_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.AUTH_UNAUTHENTICATED);
        }
        if (!user.isEnabled()) {
            throw new AppException(ErrorCode.USER_NOT_VERIFIED);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(token)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean expirationTimeValidated = expirationTime.after(new Date());
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            boolean verified = signedJWT.verify(verifier);
            return IntrospectResponse.builder()
                    .isValid(expirationTimeValidated && verified)
                    .build();
        } catch (ParseException e) {
            log.error("Parse token failed", e);
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            log.error("Verify token failed", e);
            throw new RuntimeException(e);
        }
    }

    private String generateToken(User user) {
        // header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(new Date())
                .expirationTime(
                        Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .jwtID(UUID.randomUUID().toString())
                .claim(JwtClaimSetConstant.CLAIM_USER_ID, user.getId())
                .claim(JwtClaimSetConstant.CLAIM_SCOPE, buildScope(user))
                .claim(JwtClaimSetConstant.CLAIM_PERMISSION, buildPermission(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // build JsonObject
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // signer
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.AUTH_GENERATION_FAIL);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        boolean isEmpty = CollectionUtils.isEmpty(user.getRoles());
        if (!isEmpty) {
            user.getRoles().forEach(role -> stringJoiner.add(role.getRoleName()));
        }
        return stringJoiner.toString();
    }

    private Set<String> buildPermission(User user) {
        HashSet<String> permissions = new HashSet<>();
        boolean isEmpty = CollectionUtils.isEmpty(user.getRoles());

        if (!isEmpty) {
            user.getRoles().forEach(
                    role -> role.getPermissions().forEach(
                            permission -> permissions.add(permission.getPermissionName())));
        }

        return permissions;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail())
                .or(() -> userRepository.findByUsername(request.getUsername()));

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.isEnabled()) {
                throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
            }
            String token = emailVerificationService.createToken(user);
            mailService.sendVerifyEmail(user.getEmail(), token);
            return;
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status(UserStatus.ACTIVE)
                .enabled(false)
                .build();

        Role userRole = roleRepository.findByRoleName(PredefinedRole.USER_ROLE)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(new HashSet<>(Set.of(userRole)));

        User userSaved = userRepository.save(user);
        profileService.createDefaultProfile(user);

        String token = emailVerificationService.createToken(userSaved);
        mailService.sendVerifyEmail(userSaved.getEmail(), token);
    }
}
