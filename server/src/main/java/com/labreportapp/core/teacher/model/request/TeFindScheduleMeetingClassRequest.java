package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeFindScheduleMeetingClassRequest {

    private String idTeacher;

}