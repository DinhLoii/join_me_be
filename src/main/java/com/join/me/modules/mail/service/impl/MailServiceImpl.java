package com.join.me.modules.mail.service.impl;

import com.join.me.modules.mail.config.MailProperties;
import com.join.me.modules.mail.constants.MailConstants;
import com.join.me.modules.mail.service.api.MailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailServiceImpl implements MailService {

    JavaMailSender mailSender;
    MailProperties mailProperties;

    @Override
    public void sendVerifyEmail(String to, String token) {
        String link = mailProperties.getFrontendUrl() + "/verify-email?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(MailConstants.VERIFY_EMAIL_SUBJECT);
        message.setText(MailConstants.VERIFY_EMAIL_TEMPLATE.formatted(link));

        mailSender.send(message);
    }
}

