package com.labreportapp.entity;

import com.labreportapp.entity.base.PrimaryEntity;
import com.labreportapp.infrastructure.constant.ClassPeriod;
import com.labreportapp.infrastructure.constant.EntityProperties;
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
@Table(name = "class")
public class Class extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column
    private Long startTime;

    @Column(length = EntityProperties.LENGTH_NAME)
    private String password;

    @Column
    private ClassPeriod classPeriod;

    @Column
    private Integer classSize;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teacherId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String activityId;

    @Column
    private Boolean statusClass;
}
