package com.labreportapp.labreport.core.teacher.wsconfig;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author hieundph25894
 */
@Controller
public class WebSocketController {

//    @MessageMapping("/teacher-ws/someTopic")
//    @SendTo("/teacher-ws/someTopic")
//    public String sendMessage(String message) {
//        System.err.println("máy chủ đã nhận "+ message);
//
//        return "Máy chủ đã nhận: " + message;
//    }
}
