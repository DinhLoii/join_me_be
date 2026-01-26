package com.join.me.modules.profile.constant.profile;

public final class ProfileErrorCodeConstants {

    // =========================
    // VALIDATION ERRORS
    // =========================
    public static final String PROFILE_FULL_NAME_NOT_BLANK = "PROFILE_FULL_NAME_NOT_BLANK";
    public static final String PROFILE_FULL_NAME_SIZE_INVALID = "PROFILE_FULL_NAME_SIZE_INVALID";
    public static final String PROFILE_BIO_SIZE_INVALID = "PROFILE_BIO_SIZE_INVALID";
    public static final String PROFILE_ADDRESS_SIZE_INVALID = "PROFILE_ADDRESS_SIZE_INVALID";
    public static final String PROFILE_SOCIAL_URL_INVALID = "PROFILE_SOCIAL_URL_INVALID";
    public static final String PROFILE_DOB_INVALID = "PROFILE_DOB_INVALID";
    public static final String PROFILE_GENDER_INVALID = "PROFILE_GENDER_INVALID";
    public static final String PROFILE_RELATIONSHIP_STATUS_INVALID = "PROFILE_RELATIONSHIP_STATUS_INVALID";
    public static final String PROFILE_BACKGROUND_URL_SIZE_INVALID = "PROFILE_BACKGROUND_URL_SIZE_INVALID";

    // =========================
    // BUSINESS LOGIC ERRORS
    // =========================
    public static final String PROFILE_NOT_FOUND = "PROFILE_NOT_FOUND";
    public static final String PROFILE_NOT_EXISTED = "PROFILE_NOT_EXISTED";
    public static final String PROFILE_ALREADY_EXISTS = "PROFILE_ALREADY_EXISTS";
    public static final String PROFILE_ACCESS_DENIED = "PROFILE_ACCESS_DENIED";
    public static final String PROFILE_UPDATE_NOT_ALLOWED = "PROFILE_UPDATE_NOT_ALLOWED";

    // =========================
    // OPERATION FAILURES
    // =========================
    public static final String PROFILE_CREATE_FAILED = "PROFILE_CREATE_FAILED";
    public static final String PROFILE_UPDATE_FAILED = "PROFILE_UPDATE_FAILED";
    public static final String PROFILE_DELETE_FAILED = "PROFILE_DELETE_FAILED";

    // =========================
    // SYSTEM / DATA ERRORS
    // =========================
    public static final String PROFILE_DATA_INTEGRITY_ERROR = "PROFILE_DATA_INTEGRITY_ERROR";
    public static final String PROFILE_MAPPING_ERROR = "PROFILE_MAPPING_ERROR";

    private ProfileErrorCodeConstants() {
        // prevent instantiation
    }
}
