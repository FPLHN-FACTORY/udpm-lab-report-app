package com.labreportapp.portalprojects.repository;

import com.labreportapp.portalprojects.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(NotificationRepository.NAME)
public interface NotificationRepository extends JpaRepository<Notification, String> {

    public static final String NAME = "BaseNotificationRepository";
}
