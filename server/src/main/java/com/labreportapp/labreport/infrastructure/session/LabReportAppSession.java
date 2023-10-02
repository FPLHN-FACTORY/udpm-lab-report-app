package com.labreportapp.labreport.infrastructure.session;

/**
 * @author thangncph26123
 */
public interface LabReportAppSession {

    String getToken();

    String getUserId();

    String getEmail();

    String getUserName();

    String getName();
}
