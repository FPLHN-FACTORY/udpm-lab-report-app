package com.labreportapp.portalprojects.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

/**
 * @author thangncph26123
 */

@Entity
@Getter
@Setter
@ToString
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class Project extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String code; // PORTAL_PROJECT
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
    private String backgroundImage;
    @Column(length =  EntityProperties.LENGTH_NAME)
    private String backgroundColor;
    @Column(nullable = false)
    private StatusProject statusProject;

}
