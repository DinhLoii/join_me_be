package com.join.me.modules.room.constant;

public final class RoomErrorCodeConstants {

    // =========================
    // VALIDATION ERRORS - ROOM
    // =========================
    public static final String ROOM_TITLE_NOT_BLANK = "ROOM_TITLE_NOT_BLANK";
    public static final String ROOM_TITLE_SIZE_INVALID = "ROOM_TITLE_SIZE_INVALID";
    public static final String ROOM_DESCRIPTION_SIZE_INVALID = "ROOM_DESCRIPTION_SIZE_INVALID";
    public static final String ROOM_MAX_SLOT_NOT_NULL = "ROOM_MAX_SLOT_NOT_NULL";
    public static final String ROOM_MAX_SLOT_INVALID = "ROOM_MAX_SLOT_INVALID";
    public static final String ROOM_JOIN_MODE_NOT_NULL = "ROOM_JOIN_MODE_NOT_NULL";
    public static final String ROOM_JOIN_MODE_INVALID = "ROOM_JOIN_MODE_INVALID";
    public static final String ROOM_STATUS_INVALID = "ROOM_STATUS_INVALID";
    public static final String ROOM_COVER_IMG_SIZE_INVALID = "ROOM_COVER_IMG_SIZE_INVALID";
    public static final String ROOM_MEETING_POINT_SIZE_INVALID = "ROOM_MEETING_POINT_SIZE_INVALID";
    public static final String ROOM_REQUIREMENTS_SIZE_INVALID = "ROOM_REQUIREMENTS_SIZE_INVALID";
    public static final String ROOM_TAGS_SIZE_INVALID = "ROOM_TAGS_SIZE_INVALID";
    public static final String ROOM_TAG_LENGTH_INVALID = "ROOM_TAG_LENGTH_INVALID";

    // =========================
    // VALIDATION ERRORS - CATEGORY
    // =========================
    public static final String CATEGORY_NAME_NOT_BLANK = "CATEGORY_NAME_NOT_BLANK";
    public static final String CATEGORY_NAME_SIZE_INVALID = "CATEGORY_NAME_SIZE_INVALID";
    public static final String CATEGORY_DESCRIPTION_SIZE_INVALID = "CATEGORY_DESCRIPTION_SIZE_INVALID";

    // =========================
    // VALIDATION ERRORS - REQUEST
    // =========================
    public static final String REQUEST_ROOM_ID_NOT_BLANK = "REQUEST_ROOM_ID_NOT_BLANK";
    public static final String REQUEST_STATUS_NOT_NULL = "REQUEST_STATUS_NOT_NULL";
    public static final String REQUEST_STATUS_INVALID = "REQUEST_STATUS_INVALID";

    // =========================
    // BUSINESS LOGIC ERRORS - ROOM
    // =========================
    public static final String ROOM_NOT_FOUND = "ROOM_NOT_FOUND";
    public static final String ROOM_ALREADY_EXISTS = "ROOM_ALREADY_EXISTS";
    public static final String ROOM_ACCESS_DENIED = "ROOM_ACCESS_DENIED";
    public static final String ROOM_FULL = "ROOM_FULL";
    public static final String ROOM_EXPIRED = "ROOM_EXPIRED";
    public static final String ROOM_UPDATE_NOT_ALLOWED = "ROOM_UPDATE_NOT_ALLOWED";
    public static final String ROOM_DELETE_NOT_ALLOWED = "ROOM_DELETE_NOT_ALLOWED";

    // =========================
    // BUSINESS LOGIC ERRORS - CATEGORY
    // =========================
    public static final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";
    public static final String CATEGORY_ALREADY_EXISTS = "CATEGORY_ALREADY_EXISTS";
    public static final String CATEGORY_NAME_EXISTED = "CATEGORY_NAME_EXISTED";

    // =========================
    // BUSINESS LOGIC ERRORS - REQUEST
    // =========================
    public static final String REQUEST_NOT_FOUND = "REQUEST_NOT_FOUND";
    public static final String REQUEST_ALREADY_EXISTS = "REQUEST_ALREADY_EXISTS";
    public static final String REQUEST_ALREADY_PROCESSED = "REQUEST_ALREADY_PROCESSED";
    public static final String REQUEST_UPDATE_NOT_ALLOWED = "REQUEST_UPDATE_NOT_ALLOWED";

    // =========================
    // BUSINESS LOGIC ERRORS - MEMBER
    // =========================
    public static final String MEMBER_NOT_FOUND = "MEMBER_NOT_FOUND";
    public static final String MEMBER_ALREADY_EXISTS = "MEMBER_ALREADY_EXISTS";
    public static final String MEMBER_IN_COOLDOWN = "MEMBER_IN_COOLDOWN";
    public static final String MEMBER_UPDATE_NOT_ALLOWED = "MEMBER_UPDATE_NOT_ALLOWED";
    public static final String MEMBER_ROLE_INVALID = "MEMBER_ROLE_INVALID";
    public static final String MEMBER_STATUS_INVALID = "MEMBER_STATUS_INVALID";

    // =========================
    // OPERATION FAILURES
    // =========================
    public static final String ROOM_CREATE_FAILED = "ROOM_CREATE_FAILED";
    public static final String ROOM_UPDATE_FAILED = "ROOM_UPDATE_FAILED";
    public static final String ROOM_DELETE_FAILED = "ROOM_DELETE_FAILED";
    public static final String CATEGORY_CREATE_FAILED = "CATEGORY_CREATE_FAILED";
    public static final String CATEGORY_UPDATE_FAILED = "CATEGORY_UPDATE_FAILED";
    public static final String REQUEST_CREATE_FAILED = "REQUEST_CREATE_FAILED";
    public static final String REQUEST_UPDATE_FAILED = "REQUEST_UPDATE_FAILED";
    public static final String MEMBER_CREATE_FAILED = "MEMBER_CREATE_FAILED";
    public static final String MEMBER_UPDATE_FAILED = "MEMBER_UPDATE_FAILED";

    // =========================
    // SYSTEM / DATA ERRORS
    // =========================
    public static final String ROOM_DATA_INTEGRITY_ERROR = "ROOM_DATA_INTEGRITY_ERROR";
    public static final String ROOM_MAPPING_ERROR = "ROOM_MAPPING_ERROR";

    private RoomErrorCodeConstants() {
        // prevent instantiation
    }
}

