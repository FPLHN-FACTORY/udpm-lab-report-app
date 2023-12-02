package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
import com.labreportapp.labreport.infrastructure.constant.StatusClass;
import com.labreportapp.labreport.infrastructure.constant.StatusHoneyPlus;
import com.labreportapp.labreport.infrastructure.constant.StatusTeacherEdit;
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

    @Column(length = EntityProperties.LENGTH_ID)
    private String classPeriod;

    @Column
    private Integer classSize;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teacherId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String activityId;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String reasons;

    @Column
    private StatusClass statusClass;

    @Column
    private StatusTeacherEdit statusTeacherEdit;

    @Column
    private StatusHoneyPlus statusHoneyPlus;

}
