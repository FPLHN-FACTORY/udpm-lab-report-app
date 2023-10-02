package com.labreportapp.labreport.core.common.base;

import com.labreportapp.labreport.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int size = PaginationConstant.DEFAULT_SIZE;
}
