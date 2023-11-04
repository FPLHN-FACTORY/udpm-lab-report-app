package com.labreportapp.labreport.core.admin.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author quynhncph26201
 */
@Getter
@Setter
@ToString
public class AdObjFeedbackResponse {

    private String codeClass;

    private List<AdGetFeedbackResponse> listFeedback;
}
