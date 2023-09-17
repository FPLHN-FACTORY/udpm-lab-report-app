package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(MeetingRepository.NAME)
public interface MeetingRepository extends JpaRepository<Meeting, String> {

    String NAME = "BaseMeetingRepository";
}
