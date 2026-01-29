package com.join.me.modules.auth.provider;

import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.auth.constants.AuthConstants;
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
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtVerifyTokenProvider {

    @NonFinal
    @Value("${app.verify-jwt.secret}")
    String secret;

    @NonFinal
    @Value("${app.verify-jwt.expire-minutes:30}")
    long expireMinutes;

    public VerifyToken generate(String userId) {
        String jti = UUID.randomUUID().toString();
        Instant now = Instant.now();
        Instant expiresAt = now.plus(expireMinutes, ChronoUnit.MINUTES);

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userId)
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiresAt))
                .jwtID(jti)
                .claim("type", AuthConstants.VERIFY_TOKEN_TYPE_CLAIM)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(secret.getBytes()));
            String token = jwsObject.serialize();
            return new VerifyToken(token, jti, expiresAt);
        } catch (JOSEException e) {
            log.error("Cannot generate verify token", e);
            throw new AppException(ErrorCode.AUTH_GENERATION_FAIL);
        }
    }

    public JWTClaimsSet parse(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            boolean verified = signedJWT.verify(verifier);

            if (!verified) {
                throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID);
            }

            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            Date expirationTime = claimsSet.getExpirationTime();
            if (expirationTime.before(new Date())) {
                throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_EXPIRED);
            }

            return claimsSet;
        } catch (ParseException e) {
            log.error("Parse verify token failed", e);
            throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID);
        } catch (JOSEException e) {
            log.error("Verify token failed", e);
            throw new AppException(ErrorCode.EMAIL_VERIFICATION_TOKEN_INVALID);
        }
    }

    public record VerifyToken(String token, String jti, Instant expiresAt) {}
}

