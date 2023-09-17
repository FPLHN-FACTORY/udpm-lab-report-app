package com.labreportapp.portalprojects.core.member.model.response;

import com.labreportapp.portalprojects.entity.Notification;
import com.labreportapp.portalprojects.entity.NotificationMember;
import com.labreportapp.portalprojects.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {NotificationMember.class, Notification.class})
public interface MeNotificationMemberResponse extends IsIdentified {

    @Value("#{target.notification_id}")
    String getNotificationId();

    @Value("#{target.content}")
    String getContent();

    @Value("#{target.url}")
    String getUrl();

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.created_date}")
    String getCreatedDate();
}
