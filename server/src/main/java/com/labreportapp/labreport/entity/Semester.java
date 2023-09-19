package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
import com.labreportapp.labreport.infrastructure.constant.StatusFeedBack;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "semester")
public class Semester extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String name;

    @Column
    private Long startTimeStudent;

    @Column
    private Long endTimeStudent;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Column
    private StatusFeedBack statusFeedBack;
}