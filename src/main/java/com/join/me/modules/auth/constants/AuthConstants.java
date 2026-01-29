package com.join.me.modules.auth.constants;

public class AuthConstants {
    private AuthConstants() {}

    public static final String EMAIL_VERIFICATION_TOKENS = "email_verification_tokens";

    public static final String VERIFY_TOKEN_TYPE_CLAIM = "EMAIL_VERIFY";
    public static final long DEFAULT_VERIFY_TOKEN_EXPIRE_MINUTES = 30L;

    public static final String JTIHASH = "jti_hash";
    public static final String USER_ID = "user_id";
    public static final String EXPIRES_AT = "expires_at";
    public static final String VERIFIED_AT = "verified_at";
}

