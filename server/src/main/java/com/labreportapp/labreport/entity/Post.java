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
@Table(name = "post")
public class Post extends PrimaryEntity {

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teacherId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String classId;
}