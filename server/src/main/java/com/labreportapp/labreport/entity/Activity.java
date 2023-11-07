package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.AllowUseTrello;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
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
@Table(name = "activity")
public class Activity extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String name;

    @Column
    private Long startTime;

    @Column
    private Long endTime;

    @Column(length = EntityProperties.LENGTH_ID)
    private String levelId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String semesterId;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column
    private AllowUseTrello allowUseTrello;
    
}
