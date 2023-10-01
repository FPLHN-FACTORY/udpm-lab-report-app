package com.labreportapp.labreport.core.teacher.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author hieundph25894
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeSentStudentClassRequest {

    @NotNull
    private List<String> listIdStudent;

    @NotEmpty
    private String idClassSent;

    @NotEmpty
    private String idClassOld;

}
