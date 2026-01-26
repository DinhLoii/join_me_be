package com.join.me.modules.user.constant.role;

public final class RoleConstants {

    // ===== TABLE =====
    public static final String TABLE_ROLE = "roles";
    public static final String TABLE_ROLE_PERMISSION = "role_permissions";

    // ===== COLUMNS =====
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";

    public static final String COL_ROLE_ID = "role_id";
    public static final String COL_PERMISSION_ID = "permission_id";

    // ===== DEFINITIONS =====
    public static final String NAME_DEFINITION = "VARCHAR(150)";
    public static final String DESCRIPTION_DEFINITION = "VARCHAR(255)";

    private RoleConstants() {

    }
}
