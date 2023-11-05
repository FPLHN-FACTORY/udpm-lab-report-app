package com.labreportapp.labreport.infrastructure.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author todo thangncph26123
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoggerObject implements Serializable {

    private String ip;

    private String mail;

    private String method;

    private String createDate;

    private String author;

    private String pathFile;

    private String request;

    private String content;

}