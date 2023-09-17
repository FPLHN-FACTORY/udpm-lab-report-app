package com.labreportapp.portalprojects.infrastructure.successnotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

/**
 * @author thangncph26123
 */
@Component
public class SuccessNotificationSender {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void senderNotification(String message, StompHeaderAccessor headerAccessor) {
        SuccessModel successModel = new SuccessModel(message);
        String sessionId = headerAccessor.getSessionId();
        simpMessagingTemplate.convertAndSend("/portal-projects/success/" + sessionId, successModel);
    }
}
