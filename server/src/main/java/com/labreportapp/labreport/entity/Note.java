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
@Table(name = "note")
public class Note extends PrimaryEntity {

//    @Column(length = EntityProperties.LENGTH_NAME)
//    @Nationalized
//    private String name;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String meetingId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teamId;

}
