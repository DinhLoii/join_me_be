package com.join.me.common.configs.auditing;

import com.join.me.modules.auth.utils.AuthUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = AuthUtils.getCurrentUserId();
        return Optional.of(userId);
    }
}
