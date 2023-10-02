package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdFeedBackCustom {

    private Integer stt;

    private String description;

    private String idStudent;

    private String nameStudent;

    private String emailStudent;

    private String idClass;

    private Long createdDate;
}
