package com.labreportapp.portalprojects.entity;

import com.labreportapp.portalprojects.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import com.labreportapp.portalprojects.infrastructure.constant.StatusPeriod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Nationalized;

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "period")
@AllArgsConstructor
@NoArgsConstructor
public class Period extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String code;

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String name;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String descriptions;

    @Column(nullable = false)
    private Long startTime;

    @Column(nullable = false)
    private Long endTime;

    private Float progress;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String target;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_projectId")
    private String projectId;

    @Column(nullable = false)
    private StatusPeriod statusPeriod;
}
