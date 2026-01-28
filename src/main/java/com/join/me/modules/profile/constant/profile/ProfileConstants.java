package com.join.me.modules.profile.constant.profile;

public final class ProfileConstants {

    // =========================
    // TABLE
    // =========================
    public static final String TABLE_PROFILE = "profiles";

    // =========================
    // COLUMNS
    // =========================
    public static final String COL_USER_ID = "user_id";
    public static final String COL_FULL_NAME = "full_name";
    public static final String COL_AVATAR_URL = "avatar_url";
    public static final String COL_BIO = "bio";
    public static final String COL_ADDRESS = "address";
    public static final String COL_SOCIAL_URLS = "social_url"; // ERD: social_url[]
    public static final String COL_DOB = "dob";
    public static final String COL_RELATIONSHIP_STATUS = "relationship_status";
    public static final String COL_BACKGROUND_URL = "background_url";
    public static final String COL_GENDER = "gender";
    public static final String COL_EDUCATION_LEVEL = "education_level";
    public static final String COL_HOBBIES = "hobbies"; // ERD: hobbies[]

    // =========================
    // DEFINITIONS
    // =========================
    public static final String USER_ID_DEFINITION = "VARCHAR(36)";
    public static final String FULL_NAME_DEFINITION = "VARCHAR(150)";
    public static final String AVATAR_URL_DEFINITION = "VARCHAR(500)";
    public static final String BIO_DEFINITION = "VARCHAR(500)";
    public static final String ADDRESS_DEFINITION = "VARCHAR(255)";
    public static final String SOCIAL_URLS_DEFINITION = "TEXT";
    public static final String DOB_DEFINITION = "DATE";
    public static final String RELATIONSHIP_STATUS_DEFINITION = "VARCHAR(50)";
    public static final String BACKGROUND_URL_DEFINITION = "VARCHAR(500)";
    public static final String GENDER_DEFINITION = "VARCHAR(20)";
    public static final String EDUCATION_LEVEL_DEFINITION = "VARCHAR(255)";
    public static final String HOBBIES_DEFINITION = "TEXT";

    // =========================
    // VALIDATION (for DTO)
    // =========================
    public static final int FULL_NAME_MIN = 2;
    public static final int FULL_NAME_MAX = 150;

    public static final int AVATAR_URL_MAX_LENGTH = 500;
    public static final int BIO_MAX_LENGTH = 500;
    public static final int ADDRESS_MAX_LENGTH = 255;

    /**
     * social_url[] theo ERD nhưng lưu 1 cột TEXT/JSON string
     */
    public static final int SOCIAL_URLS_MAX_LENGTH = 2000;

    public static final int RELATIONSHIP_STATUS_MAX_LENGTH = 50;
    public static final int BACKGROUND_URL_MAX_LENGTH = 500;
    public static final int EDUCATION_LEVEL_MAX_LENGTH = 255;
    public static final int HOBBIES_MAX_LENGTH = 2000;

    private ProfileConstants() {
        // prevent instantiation
    }
}
