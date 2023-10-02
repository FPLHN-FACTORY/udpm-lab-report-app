package com.labreportapp.labreport.core.student.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class StStudentListFeedBackRequest {

    @NotBlank
    private String studentId;

    private List<StStudentFeedBackRequest> listFeedBack;
}
