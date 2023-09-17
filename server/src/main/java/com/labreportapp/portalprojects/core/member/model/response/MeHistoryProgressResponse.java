package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.entity.HistoryProgress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {HistoryProgress.class})
public interface MeHistoryProgressResponse {

    @Value("#{target.progress_change}")
    Float getValue();

    @Value("#{target.progress_date}")
    Long getCreatedDate();
}
