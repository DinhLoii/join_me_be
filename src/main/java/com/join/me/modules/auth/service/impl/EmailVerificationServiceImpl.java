package com.join.me.modules.auth.service.impl;

import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.auth.constants.AuthConstants;
import com.join.me.modules.auth.entity.EmailVerificationToken;
import com.join.me.modules.auth.provider.JwtVerifyTokenProvider;
import com.join.me.modules.auth.repository.EmailVerificationTokenRepository;
import com.join.me.modules.auth.service.api.EmailVerificationService;
import com.join.me.modules.auth.utils.HashUtil;
import com.join.me.modules.user.entity.User;
import com.join.me.modules.user.repository.UserRepository;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationServiceImpl implements EmailVerificationService {

    JwtVerifyTokenProvider tokenProvider;
    EmailVerificationTokenRepository tokenRepository;
    UserRepository userRepository;

    @Override
    @Transactional
    public String createToken(User user) {
        tokenRepository.deleteByUser(user);

        var verifyToken = tokenProvider.generate(user.getId());
        String jtiHash = HashUtil.sha256(verifyToken.jti());

        EmailVerificationToken token = EmailVerificationToken.builder()
                .user(user)
                .jtiHash(jtiHash)
                .expiresAt(LocalDateTime.ofInstant(verifyToken.expiresAt(), ZoneOffset.UTC))
                .build();

        tokenRepository.save(token);

        return verifyToken.token();
    }

    @Override
    @Transactional
    public void verify(String rawToken) {
        JWTClaimsSet claims = tokenProvider.parse(rawToken);

        String type = claims.getClaim("type").toString();
        if (!AuthConstants.VERIFY_TOKEN_TYPE_CLAIM.equals(type)) {
            throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID);
        }

        String userId = claims.getSubject();
        String jti = claims.getJWTID();
        String jtiHash = HashUtil.sha256(jti);

        EmailVerificationToken token = tokenRepository.findByJtiHash(jtiHash)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID));

        if (token.getVerifiedAt() != null) {
            throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_ALREADY_USED);
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_EXPIRED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_USERNAME_NOT_FOUND));

        user.setEnabled(true);
        userRepository.save(user);

        token.setVerifiedAt(LocalDateTime.now());
        tokenRepository.save(token);
    }
}

