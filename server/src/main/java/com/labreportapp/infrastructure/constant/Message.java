package com.labreportapp.infrastructure.constant;

import com.labreportapp.util.PropertiesReader;

/**
 * @author thangncph26123
 */

public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),

    // CLASS_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeyss.CLASS_NOT_EXISTS));
    CLASS_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeyss.CLASS_NOT_EXISTS)),
    TEAM_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeyss.TEAM_NOT_EXISTS)),
    CODE_TEAM_EXISTS(PropertiesReader.getProperty(PropertyKeyss.CODE_TEAM_EXISTS)),
    SEMESTER_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeys.SEMESTER_NOT_EXISTS));

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
