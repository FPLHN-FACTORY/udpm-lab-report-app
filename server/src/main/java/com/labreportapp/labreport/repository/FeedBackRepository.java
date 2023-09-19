package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(FeedBackRepository.NAME)
public interface FeedBackRepository extends JpaRepository<FeedBack, String> {

    String NAME = "BaseFeedBackRepository";
}
