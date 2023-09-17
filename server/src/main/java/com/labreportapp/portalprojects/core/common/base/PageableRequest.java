package com.labreportapp.portalprojects.core.common.base;

import com.labreportapp.portalprojects.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int size = PaginationConstant.DEFAULT_SIZE;
}
