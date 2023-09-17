package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.NotificationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(NotificationMemberRepository.NAME)
public interface NotificationMemberRepository extends JpaRepository<NotificationMember, String> {

    public static final String NAME = "BaseNotificationMemberRepository";
}
