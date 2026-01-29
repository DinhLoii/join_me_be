package com.join.me.modules.auth.scheduler;

import com.join.me.modules.auth.repository.EmailVerificationTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailVerificationTokenCleanupJob {

    EmailVerificationTokenRepository tokenRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void cleanExpiredTokens() {
        try {
            LocalDateTime now = LocalDateTime.now();
            tokenRepository.deleteAllExpired(now);
            log.info("Cleaned up expired email verification tokens at {}", now);
        } catch (Exception e) {
            log.error("Error cleaning up expired email verification tokens", e);
        }
    }
}

