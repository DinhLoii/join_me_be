package com.join.me.modules.room.constant;

public final class RoomConstants {

    // =========================
    // TABLE
    // =========================
    public static final String TABLE_ROOM = "rooms";
    public static final String TABLE_CATEGORY = "categories";
    public static final String TABLE_ROOM_JOIN_REQUEST = "room_join_request";
    public static final String TABLE_ROOM_MEMBER = "room_member";
    public static final String TABLE_ROOM_CATEGORIES = "room_categories";

    // =========================
    // COLUMNS - ROOM
    // =========================
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_MAX_SLOT = "max_slot";
    public static final String COL_JOIN_MODE = "join_mode";
    public static final String COL_STATUS = "status";
    public static final String COL_EXPIRES_AT = "expires_at";
    public static final String COL_COVER_IMG = "cover_img";
    public static final String COL_MEETING_POINT = "meeting_point";
    public static final String COL_OWNER_ID = "owner_id";

    // =========================
    // COLUMNS - CATEGORY
    // =========================
    public static final String COL_NAME = "name";

    // =========================
    // COLUMNS - ROOM_JOIN_REQUEST
    // =========================
    public static final String COL_ROOM_ID = "room_id";
    public static final String COL_USER_ID = "user_id";

    // =========================
    // COLUMNS - ROOM_MEMBER
    // =========================
    public static final String COL_ROLE = "role";
    public static final String COL_JOINED_AT = "joined_at";
    public static final String COL_LEFT_AT = "left_at";
    public static final String COL_COOLDOWN_UNTIL = "cooldown_until";

    // =========================
    // DEFINITIONS
    // =========================
    public static final String TITLE_DEFINITION = "VARCHAR(200)";
    public static final String DESCRIPTION_DEFINITION = "TEXT";
    public static final String MAX_SLOT_DEFINITION = "INTEGER";
    public static final String JOIN_MODE_DEFINITION = "VARCHAR(20)";
    public static final String STATUS_DEFINITION = "VARCHAR(20)";
    public static final String EXPIRES_AT_DEFINITION = "TIMESTAMP";
    public static final String COVER_IMG_DEFINITION = "VARCHAR(500)";
    public static final String MEETING_POINT_DEFINITION = "VARCHAR(255)";
    public static final String OWNER_ID_DEFINITION = "VARCHAR(36)";
    public static final String NAME_DEFINITION = "VARCHAR(100)";
    public static final String ROOM_ID_DEFINITION = "VARCHAR(36)";
    public static final String USER_ID_DEFINITION = "VARCHAR(36)";
    public static final String ROLE_DEFINITION = "VARCHAR(20)";
    public static final String JOINED_AT_DEFINITION = "TIMESTAMP";
    public static final String LEFT_AT_DEFINITION = "TIMESTAMP";
    public static final String COOLDOWN_UNTIL_DEFINITION = "TIMESTAMP";
    public static final String REQUIREMENTS_DEFINITION = "TEXT";
    public static final String TAGS_DEFINITION = "TEXT";

    // =========================
    // VALIDATION (for DTO)
    // =========================
    public static final int TITLE_MIN = 1;
    public static final int TITLE_MAX = 200;
    public static final int DESCRIPTION_MAX = 5000;
    public static final int MAX_SLOT_MIN = 1;
    public static final int MAX_SLOT_MAX = 1000;
    public static final int COVER_IMG_MAX_LENGTH = 500;
    public static final int MEETING_POINT_MAX_LENGTH = 255;
    public static final int NAME_MIN = 1;
    public static final int NAME_MAX = 100;
    public static final int DESCRIPTION_CATEGORY_MAX = 1000;
    public static final int REQUIREMENTS_MAX_SIZE = 50;
    public static final int TAGS_MAX_SIZE = 20;
    public static final int TAG_MAX_LENGTH = 50;

    // =========================
    // BUSINESS CONSTANTS
    // =========================
    public static final int DEFAULT_EXPIRY_DAYS = 3;
    public static final int COOLDOWN_MINUTES = 1;

    private RoomConstants() {
        // prevent instantiation
    }
}

