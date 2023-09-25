package com.labreportapp.labreport.core.admin.service;

import com.labreportapp.labreport.core.admin.model.request.*;
import com.labreportapp.labreport.core.admin.model.response.AdLevelResponse;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.entity.Level;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author quynhncph26201
 */
public interface AdLevelService {
    List<Level> findAllLevel(Pageable pageable);

    Level createLevel(@Valid final AdCreateLevelRequest obj);

    Level updateLevel(final AdUpdateLevelRequest obj);

    PageableObject<AdLevelResponse> searchLevel(final AdFindLevelRequest rep);

    Boolean deleteLevel(final String id);
}
