package com.labreportapp.infrastructure.constant;

import com.labreportapp.util.PropertiesReader;

/**
 * @author thangncph26123
 */

public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),

    // CLASS_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeyss.CLASS_NOT_EXISTS));
    CLASS_STUDENT_IS_EMPTY(PropertiesReader.getProperty(PropertyKeys.CLASS_STUDENT_IS_EMPTY)),
    DESCRIPTIONS_IS_EMPTY(PropertiesReader.getProperty(PropertyKeys.DESCRIPTIONS_IS_EMPTY)),
    CLASS_IS_EMPTY(PropertiesReader.getProperty(PropertyKeys.CLASS_IS_EMPTY)),
    POST_IS_EXIST(PropertiesReader.getProperty(PropertyKeys.POST_IS_EXIST)),
    MEETING_HAS_NOT_COME(PropertiesReader.getProperty(PropertyKeys.MEETING_HAS_NOT_COME)),
    MEETING_IS_OVER(PropertiesReader.getProperty(PropertyKeys.MEETING_IS_OVER)),
    SCHEDULE_TODAY_IS_EMPTY(PropertiesReader.getProperty(PropertyKeys.SCHEDULE_TODAY_IS_EMPTY)),
    CODE_TEAM_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_TEAM_EXISTS)),
    SEMESTER_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.SEMESTER_NOT_EXISTS)),
    SEMESTER_ACTIVITY_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.SEMESTER_ACTIVITY_ALREADY_EXISTS)),
    CLASS_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.CLASS_NOT_EXISTS)),
    MEETING_HOMEWORK_NOTE_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.MEETING_HOMEWORK_NOTE_NOT_EXISTS)),
    MEETING_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.MEETING_NOT_EXISTS)),
    TEAM_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.TEAM_NOT_EXISTS)),
    UNIQUE_LEADER_TEAM(PropertiesReader.getProperty(PropertyKeys.UNIQUE_LEADER_TEAM)),
    INVALID_DATE(PropertiesReader.getProperty(PropertyKeys.INVALID_DATE)),
    DANG_CO_DU_LIEU_LIEN_QUAN_KHONG_THE_XOA_BUOI_HOC(PropertiesReader.getProperty(PropertyKeys.DANG_CO_DU_LIEU_LIEN_QUAN_KHONG_THE_XOA_BUOI_HOC)),
    ACTIVITY_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.ACTIVITY_NOT_EXISTS)),
    STUDENT_CLASSES_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.STUDENT_CLASSES_NOT_EXISTS)),
    STUDENT_HAD_TEAM(PropertiesReader.getProperty(PropertyKeys.STUDENT_HAD_TEAM)),
    STUDENT_HAD_NOT_TEAM(PropertiesReader.getProperty(PropertyKeys.STUDENT_HAD_NOT_TEAM)),
    CHUA_DEN_THOI_GIAN_CUA_BUOI_HOC(PropertiesReader.getProperty(PropertyKeys.CHUA_DEN_THOI_GIAN_CUA_BUOI_HOC)),
    ACTIVITY_HAVE_CLASS(PropertiesReader.getProperty(PropertyKeys.ACTIVITY_HAVE_CLASS)),
    YOU_HAD_IN_CLASS(PropertiesReader.getProperty(PropertyKeys.YOU_HAD_IN_CLASS)),
    CLASS_DID_FULL_CLASS_SIZE(PropertiesReader.getProperty(PropertyKeys.CLASS_DID_FULL_CLASS_SIZE)),
    YOU_DONT_LEAVE_CLASS(PropertiesReader.getProperty(PropertyKeys.YOU_DONT_LEAVE_CLASS)),
    CODE_CLASS_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_CLASS_EXISTS)),
    YOU_MUST_LEADER(PropertiesReader.getProperty(PropertyKeys.YOU_MUST_LEADER));

    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
