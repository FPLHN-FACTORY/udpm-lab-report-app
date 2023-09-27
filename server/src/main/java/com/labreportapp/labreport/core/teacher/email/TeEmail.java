package com.labreportapp.labreport.core.teacher.email;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeEmail {

    private String[] toEmail;
    private String subject;
    private String body;
    private String titleEmail;

}
