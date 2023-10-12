package com.labreportapp.labreport.core.teacher.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hieundph25894
 */
@RestController
public class WebSocketController {

    @MessageMapping("/update-meeting")
    @SendTo("/portal-projects/update-meeting")
    public String sendNotification(String message) {
        return message;
    }
}

