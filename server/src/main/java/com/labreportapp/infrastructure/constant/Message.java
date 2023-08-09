package com.labreportapp.infrastructure.constant;

import com.labreportapp.util.PropertiesReader;

/**
 * @author thangncph26123
 */

public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown");
     //  CREATE_PERIOD_BEFORE_CREATE_TODO(PropertiesReader.getProperty(PropertyKeys.CREATE_PERIOD_BEFORE_CREATE_TODO));
   // CLASS_NOT_EXISTS(PropertiesReader.getProperty(PropertyKeyss.CLASS_NOT_EXISTS));
    public static final String CLASS_NOT_EXISTS = PropertiesReader.getProperty(PropertyKeyss.CLASS_NOT_EXISTS);
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
