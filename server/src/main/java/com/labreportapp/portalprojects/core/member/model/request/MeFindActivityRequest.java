package com.labreportapp.portalprojects.core.member.model.request;

import com.labreportapp.portalprojects.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeFindActivityRequest extends PageableRequest {

    private String idTodo;
}
