package com.labreportapp.labreport.core.teacher.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher/email")

public class TeEmailSenderController {

    @Autowired
    private TeEmailSender teEmailSender;

    @PostMapping()
    public ResponseEntity<?> sendMail(@RequestBody TeEmail teEmail) {
        teEmailSender.sendEmail(teEmail.getToEmail(), teEmail.getSubject(), teEmail.getBody());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}