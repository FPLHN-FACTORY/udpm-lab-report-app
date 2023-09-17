package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(ReasonRepository.NAME)
public interface ReasonRepository extends JpaRepository<Reason, String> {

    public static final String NAME = "BaseReasonRepository";
}
