package com.labreportapp.labreport.infrastructure.logger;

import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author todo thangncph26123
 */

@Service
public class LogService {

    private final RabbitTemplate amqpTemplate;

    @Value("${rabbit.route.key}")
    private String routeQueue;

    @Value("${rabbit.topic.exchange}")
    private String exchangeQueue;

    @Autowired
    public LogService(RabbitTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    // Gửi log thông điệp vào RabbitMQ
    public void sendLogMessage(LoggerObject loggerObject) {
        Gson gson = new Gson();
        String message = gson.toJson(loggerObject);
        amqpTemplate.convertAndSend(exchangeQueue, routeQueue, message, messagePostProcessor -> {
            messagePostProcessor.getMessageProperties();
            return messagePostProcessor;
        });
    }
}
