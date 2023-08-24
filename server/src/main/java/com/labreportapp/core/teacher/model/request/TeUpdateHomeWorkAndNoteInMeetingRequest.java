package com.labreportapp.core.teacher.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@Getter
@Setter
public class TeUpdateHomeWorkAndNoteInMeetingRequest {

    @NotEmpty
    @NotBlank
    private String idHomeWork;

    private String descriptionsHomeWork;

    @NotBlank
    @NotEmpty
    private String idNote;

    private String descriptionsNote;

}
