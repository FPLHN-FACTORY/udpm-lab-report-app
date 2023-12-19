package com.labreportapp.labreport.core.teacher.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hieundph25894 - duchieu212
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TeUpdateFiledClassRequest {

    private String idClass;

    private String descriptions;

    private String classPeriod;

}
