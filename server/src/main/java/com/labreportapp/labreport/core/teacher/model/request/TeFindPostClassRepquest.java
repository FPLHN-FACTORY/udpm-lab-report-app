package com.labreportapp.labreport.core.teacher.model.request;

import com.labreportapp.labreport.core.common.base.PageableRequest;
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
