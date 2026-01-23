package com.join.me.modules.user.constant.permission;

public final class PermissionConstants {

    // ===== TABLE =====
    public static final String TABLE_PERMISSION = "permissions";

    // ===== COLUMNS =====
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_EMAIL = "email";
    public static final String COL_STATUS = "status";

    // ===== DEFINITIONS =====
    public static final String NAME_DEFINITION = "VARCHAR(150)";
    public static final String DESCRIPTION_DEFINITION = "VARCHAR(255)";
    public static final String EMAIL_DEFINITION = "VARCHAR(255)";
    public static final String STATUS_DEFINITION = "VARCHAR(50)";

    private PermissionConstants() {
        // prevent instantiation
    }
}
