package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "meeting_period")
public class MeetingPeriod extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String name;

    @Column(length = EntityProperties.LENGTH_TIME)
    private Integer startHour;

    @Column(length = EntityProperties.LENGTH_TIME)
    private Integer startMinute;

    @Column(length = EntityProperties.LENGTH_TIME)
    private Integer endHour;

    @Column(length = EntityProperties.LENGTH_TIME)
    private Integer endMinute;

}
