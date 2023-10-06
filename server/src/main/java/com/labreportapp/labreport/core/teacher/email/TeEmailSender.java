package com.labreportapp.labreport.core.teacher.email;

import com.labreportapp.labreport.infrastructure.constant.MailConstant;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author hieundph25894
 */
@Slf4j
@Service
public class TeEmailSender {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public static final String LOGO_PATH_SMALL = "/static/images/logo.png";

    public void convertHtmlSendEmail(String[] toEmails, String subject, String titleEmail, String bodyEmail) {
        String htmlBody = MailConstant.BODY_STARTS +
                titleEmail +
                MailConstant.BODY_BODY +
                bodyEmail +
                MailConstant.BODY_END;
        sendEmail(toEmails, subject, htmlBody);
    }

    @Async
    public void sendEmail(String[] toEmail, String subject, String bodyContend) throws MailException {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(LOGO_PATH_SMALL);
            helper.setFrom(sender);
            helper.setBcc(toEmail);
            helper.setSubject(subject);
            helper.setText(bodyContend, true);
            helper.addInline("logoImage", resource);
            emailSender.send(message);
        } catch (Exception e) {
            log.error("ERROR WHILE SENDING MAIL: {}", e.getMessage());
        }
    }
}



