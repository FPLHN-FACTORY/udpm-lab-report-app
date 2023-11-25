package com.labreportapp.labreport.repository;

import com.labreportapp.labreport.entity.MeetingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author todo thangncph26123
 */
@Repository(MeetingRequestRepository.NAME)
public interface MeetingRequestRepository extends JpaRepository<MeetingRequest, String> {

    String NAME = "BaseMeetingRequestRepository";
}
