package com.labreportapp.labreport.core.admin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
public class AdBaseTeamRequest {
    @NotEmpty
    @NotBlank
    @Length(max = 500)
    private String name;

    @NotEmpty
    @NotBlank
    @Length(max = 500)
    private String subjectName;


}
