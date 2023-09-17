package com.labreportapp.labreport.infrastructure.configemail;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Setter
@Getter
public class Email {

    private String [] toEmail;
    private String subject;
    private String body;
    private String titleEmail;
}
