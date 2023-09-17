package com.labreportapp.labreport.core.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author thangncph26123
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableObject<T> {

    private List<T> data;
    private long totalPages;
    private int currentPage;

    public PageableObject(Page<T> page) {
        this.data = page.getContent();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}
