package com.labreportapp.labreport.core.teacher.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hieundph25894
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeUpdatePostRequest {

    private String id;

    private String descriptions;

}
