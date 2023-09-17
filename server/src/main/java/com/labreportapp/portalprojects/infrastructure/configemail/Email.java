package com.labreportapp.portalprojects.infrastructure.configemail;

import lombok.Getter;
import lombok.Setter;

/**
 * @author NguyenVinh
 */
@Setter
@Getter
public class Email {

    private String [] toEmail;
    private String subject;
    private String body;
    private String titleEmail;
}
