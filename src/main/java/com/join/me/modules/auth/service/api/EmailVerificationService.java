package com.join.me.modules.auth.service.api;

import com.join.me.modules.user.entity.User;

public interface EmailVerificationService {
    String createToken(User user);
    void verify(String rawToken);
}

