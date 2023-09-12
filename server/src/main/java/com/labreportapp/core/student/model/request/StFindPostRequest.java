package com.labreportapp.core.student.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author quynhncph26201
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StFindPostRequest extends PageableRequest {

    private String idClass;

    private String idTeacher;

}
