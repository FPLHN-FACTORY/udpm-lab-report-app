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
@Table(name = "point")
public class Point extends PrimaryEntity {

    @Column
    private Double checkPointPhase1;

    @Column
    private Double checkPointPhase2;

    @Column
    private Double finalPoint;

    @Column(length = EntityProperties.LENGTH_ID)
    private String studentId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String classId;

}
