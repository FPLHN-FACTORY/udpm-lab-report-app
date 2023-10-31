package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
import com.labreportapp.labreport.infrastructure.constant.StatusShowFeedback;
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
@Table(name = "feed_back")
public class FeedBack extends PrimaryEntity {

    @Column
    private Integer rateQuestion1;

    @Column
    private Integer rateQuestion2;

    @Column
    private Integer rateQuestion3;

    @Column
    private Integer rateQuestion4;

    @Column
    private Integer rateQuestion5;

    @Column
    private Float averageRate;

    @Column
    private StatusShowFeedback status;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String classId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String studentId;

}
