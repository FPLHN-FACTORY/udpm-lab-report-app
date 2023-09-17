package com.labreportapp.portalprojects.entity;

import com.labreportapp.portalprojects.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "history_progress")
@AllArgsConstructor
@NoArgsConstructor
public class HistoryProgress extends PrimaryEntity {

    private Float progressChange;

    private Float progressTotal;

    @Column(nullable = false)
    private Long progressDate;

    @Column(length = EntityProperties.LENGTH_ID)
    private String projectId;
}
