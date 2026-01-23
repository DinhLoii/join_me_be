package com.join.me.modules.user.constant.permission;

public final class PermissionConstants {

    // ===== TABLE =====
    public static final String TABLE_PERMISSION = "permissions";

    // ===== COLUMNS =====
    public static final String COL_CODE = "code";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";

    // ===== DEFINITIONS =====
    public static final String CODE_DEFINITION = "VARCHAR(100)";
    public static final String NAME_DEFINITION = "VARCHAR(150)";
    public static final String DESCRIPTION_DEFINITION = "VARCHAR(255)";

    private PermissionConstants() {
        throw new UnsupportedOperationException("PermissionConstants should not be instantiated");
    }
}