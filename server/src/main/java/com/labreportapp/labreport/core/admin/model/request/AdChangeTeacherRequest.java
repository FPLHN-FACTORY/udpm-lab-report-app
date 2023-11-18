package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdChangeTeacherRequest {

    @NotEmpty
    private List<String> listMeeting;

    @NotBlank
    private String teacherId;

}
