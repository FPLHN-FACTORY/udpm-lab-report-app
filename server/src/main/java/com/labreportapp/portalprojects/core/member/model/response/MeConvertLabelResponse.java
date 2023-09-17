package com.labreportapp.portalprojects.core.member.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author thangncph26123
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeConvertLabelResponse {

    private String id;

    private String code;

    private String name;

    private String colorLabel;
}
