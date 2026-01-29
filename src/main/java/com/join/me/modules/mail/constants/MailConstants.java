package com.join.me.modules.mail.constants;

public class MailConstants {
    private MailConstants() {}

    public static final String VERIFY_EMAIL_SUBJECT = "Verify your email";
    public static final String VERIFY_EMAIL_TEMPLATE = """
        Welcome!
        Please verify your email by clicking the link below:
        
        %s
        
        This link expires in 30 minutes.
        """;
}

