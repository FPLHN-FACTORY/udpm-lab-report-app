package com.labreportapp.labreport.infrastructure.session;

import com.labreportapp.labreport.infrastructure.constant.SessionConstant;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public class LabReportAppSessionImpl implements LabReportAppSession {

    @Autowired
    private HttpSession session;

    @Override
    public String getToken() {
        return String.valueOf(session.getAttribute(SessionConstant.TOKEN));
    }

}
