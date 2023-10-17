package com.labreportapp.portalprojects.infrastructure.constant;

import com.labreportapp.portalprojects.util.PropertiesReader;

/**
 * @author thangncph26123
 */

public enum Message {

    SUCCESS("Success"),

    //    ERROR_UNKNOWN("Error Unknown"),
    ERROR_UNKNOWN("Error Unknown"),
    PROJECT_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.PROJECT_NOT_EXISTS)),
    STAKEHOLDER_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.STAKEHOLDER_NOT_EXISTS)),
    USER_NAME_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.USER_NAME_ALREADY_EXISTS)),
    INVALID_DATE(PropertiesReader.getProperty(PropertyKeys.INVALID_DATE)),
    INVALID_END_TIME(PropertiesReader.getProperty(PropertyKeys.INVALID_END_TIME)),
    PERIOD_OVERLAP(PropertiesReader.getProperty(PropertyKeys.PERIOD_OVERLAP)),
    PERIOD_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.PERIOD_NOT_EXISTS)),
    MEMBER_PROJECT_DELETE(PropertiesReader.getProperty(PropertyKeys.MEMBER_PROJECT_DELETE)),
    CODE_PROJECT_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_PROJECT_ALREADY_EXISTS)),
    CODE_MENBER_PROJECT_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_MENBER_PROJECT_ALREADY_EXISTS)),
    MEMBER_PROJECT_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.MEMBER_PROJECT_NOT_EXISTS)),
    TO_DO_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.TO_DO_NOT_EXISTS)),
    DESCRIPTIONS_TOO_LONG(PropertiesReader.getProperty(PropertyKeys.DESCRIPTIONS_TOO_LONG)),
    CODE_CATEGORY_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_CATEGORY_ALREADY_EXISTS)),
    CATEGORY_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.CATEGORY_NOT_EXISTS)),
    CODE_LABEL_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.CODE_LABEL_ALREADY_EXISTS)),
    LABEL_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.LABEL_NOT_EXISTS)),
    TODO_LIST_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.TODO_LIST_NOT_EXISTS)),
    COMMENT_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.COMMENT_NOT_EXISTS)),
    IMAGE_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.IMAGE_NOT_EXISTS)),
    RESOURCE_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.RESOURCE_NOT_EXISTS)),
    USER_NOT_ALLOWED(PropertiesReader.getProperty(PropertyKeys.USER_NOT_ALLOWED)),
    START_TIME_OF_PERIOD_NO_SMALL_BETTER_START_TIME_OF_PROJECT(PropertiesReader.getProperty(PropertyKeys.START_TIME_OF_PERIOD_NO_SMALL_BETTER_START_TIME_OF_PROJECT)),
    END_TIME_OF_PERIOD_NO_BIG_BETTER_END_TIME_OF_PROJECT(PropertiesReader.getProperty(PropertyKeys.END_TIME_OF_PERIOD_NO_BIG_BETTER_END_TIME_OF_PROJECT)),
    CAN_NOT_DELETE_PERIOD_HAVE_TODO(PropertiesReader.getProperty(PropertyKeys.CAN_NOT_DELETE_PERIOD_HAVE_TODO)),
    CREATE_PERIOD_BEFORE_CREATE_TODO(PropertiesReader.getProperty(PropertyKeys.CREATE_PERIOD_BEFORE_CREATE_TODO)),
    ASSIGN_EXISTS(PropertiesReader.getProperty(PropertyKeys.ASSIGN_EXISTS)),
    ASSIGN_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.ASSIGN_NOT_EXISTS)),
    LABEL_PROJECT_TODO_EXISTS(PropertiesReader.getProperty(PropertyKeys.LABEL_PROJECT_TODO_EXISTS)),
    LABEL_PROJECT_TODO_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.LABEL_PROJECT_TODO_NOT_EXISTS)),
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
    LEVEL_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.LEVEL_NOT_EXISTS)),
    ROLE_PROJECT_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.ROLE_PROJECT_NOT_EXISTS)),

    //    INVALID_DATE(PropertiesReader.getProperty(PropertyKeys.INVALID_DATE)),
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
    YOU_MUST_LEADER(PropertiesReader.getProperty(PropertyKeys.YOU_MUST_LEADER)),
    CREATE_CLASS_FAIL(PropertiesReader.getProperty(PropertyKeys.CREATE_CLASS_FAIL)),
    ATTENDANCE_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.ATTENDANCE_NOT_EXISTS)),
    LEVEL_ACTIVITY_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.LEVEL_ACTIVITY_ALREADY_EXISTS)),
    TIME_SEMESTER_OVERLOAD(PropertiesReader.getProperty(PropertyKeys.TIME_SEMESTER_OVERLOAD)),
    TIME_STUDENT_SEMESTER_OVERLOAD(PropertiesReader.getProperty(PropertyKeys.TIME_STUDENT_SEMESTER_OVERLOAD)),
    MEETING_EDIT_ATTENDANCE_FAILD(PropertiesReader.getProperty(PropertyKeys.MEETING_EDIT_ATTENDANCE_FAILD)),
    ROLE_USER_CHANGE(PropertiesReader.getProperty(PropertyKeys.ROLE_USER_CHANGE)),
    INVALID_TOKEN(PropertiesReader.getProperty(PropertyKeys.INVALID_TOKEN)),
    MEETING_PERIOD_NOT_EXITS(PropertiesReader.getProperty(PropertyKeys.MEETING_PERIOD_NOT_EXITS)),
    NAME_IS_EMPTY(PropertiesReader.getProperty(PropertyKeys.NAME_IS_EMPTY)),
    TEAM_PROJECT_ALREADY_EXISTS(PropertiesReader.getProperty(PropertyKeys.TEAM_PROJECT_ALREADY_EXISTS)),
    GROUP_PROJECT_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.GROUP_PROJECT_NOT_EXISTS));

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
