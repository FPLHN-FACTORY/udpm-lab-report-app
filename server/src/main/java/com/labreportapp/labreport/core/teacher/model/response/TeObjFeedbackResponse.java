package com.labreportapp.labreport.core.teacher.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author hieundph25894 - duchieu212
 */
@Getter
@Setter
@ToString
public class TeObjFeedbackResponse {

    private String codeClass;

    private List<TeFeedbackResponse> listFeedback;

}
