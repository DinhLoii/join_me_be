package com.join.me.modules.mail.service.api;

public interface MailService {
    void sendVerifyEmail(String to, String token);
}

