package com.labreportapp.portalprojects.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Index;

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "notification_member")
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMember extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_memberId")
    private String memberId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String notificationId;

    private Integer status;
}
