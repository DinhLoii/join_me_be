package com.join.me.modules.user.constant.user;

public final class UserConstants {

    // =========================
    // TABLE NAMES
    // =========================
    public static final String TABLE_USER = "users";
    public static final String TABLE_USER_ROLE = "user_roles";

    // =========================
    // COLUMN NAMES (USER)
    // =========================
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_STATUS = "status";

    // =========================
    // COLUMN NAMES (JOIN TABLE)
    // =========================
    public static final String COL_ROLE_ID = "role_id";

    // =========================
    // COLUMN DEFINITIONS
    // =========================
    public static final String USERNAME_DEFINITION = "VARCHAR(50)";
    public static final String PASSWORD_DEFINITION = "VARCHAR(255)";
    public static final String STATUS_DEFINITION = "VARCHAR(30)";

    // =========================
    // VALIDATION LIMITS
    // =========================
    public static final int MIN_CHARS_USERNAME = 3;
    public static final int MAX_CHARS_USERNAME = 50;

    public static final int MIN_CHARS_PASSWORD = 8;
    public static final int MAX_CHARS_PASSWORD = 255;

    // =========================
    // PREVENT INSTANTIATION
    // =========================
    private UserConstants() {

    }
}
