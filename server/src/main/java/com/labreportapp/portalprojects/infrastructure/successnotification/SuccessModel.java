package com.labreportapp.portalprojects.infrastructure.successnotification;

/**
 * @author thangncph26123
 */
public class SuccessModel {

    private String successMessage;

    public SuccessModel(String message) {
        this.successMessage = message;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String message) {
        this.successMessage = message;
    }
}
