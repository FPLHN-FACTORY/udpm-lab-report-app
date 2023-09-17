package com.labreportapp.portalprojects.core.member.repository;

import com.labreportapp.portalprojects.entity.HistoryProgress;
import com.labreportapp.portalprojects.repository.HistoryProgressRepository;

/**
 * @author thangncph26123
 */
public interface MeHistoryProgressRepository extends HistoryProgressRepository {

    HistoryProgress findByProjectIdAndProgressDate(String projectId, Long progressDate);
}
