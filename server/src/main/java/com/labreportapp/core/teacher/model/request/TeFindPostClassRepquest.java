package com.labreportapp.core.teacher.model.request;

import com.labreportapp.core.common.base.PageableRequest;
import lombok.AllArgsConstructor;
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
public class TeFindPostClassRepquest  extends PageableRequest {

    private String idClass;

    private String idTeacher;

}
