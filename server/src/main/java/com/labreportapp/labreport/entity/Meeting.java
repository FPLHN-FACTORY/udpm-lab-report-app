package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
import com.labreportapp.labreport.infrastructure.constant.StatusMeeting;
import com.labreportapp.labreport.infrastructure.constant.TypeMeeting;
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
@Table(name = "meeting")
public class Meeting extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String name;

    @Column
    private Long meetingDate;

    @Column
    private String meetingPeriod;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String classId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teacherId;

    @Column
    private TypeMeeting typeMeeting;

    @Column(length = EntityProperties.LENGTH_NAME_SHORT)
    private String address;

    @Column
    private StatusMeeting statusMeeting;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String notes;

}
