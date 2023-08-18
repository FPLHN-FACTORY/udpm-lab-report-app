package com.labreportapp.entity;

import com.labreportapp.entity.base.PrimaryEntity;
import com.labreportapp.infrastructure.constant.EntityProperties;
import com.labreportapp.infrastructure.constant.MeetingPeriod;
import com.labreportapp.infrastructure.constant.TypeMeeting;
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
    private MeetingPeriod meetingPeriod;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "index_meeting_team_id")
    private String classId;

    @Column
    private TypeMeeting typeMeeting;

    @Column(length = EntityProperties.LENGTH_NAME_SHORT)
    private String address;

}
