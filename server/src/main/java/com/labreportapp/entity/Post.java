package com.labreportapp.entity;

import com.labreportapp.entity.base.PrimaryEntity;
import com.labreportapp.infrastructure.constant.EntityProperties;
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
@Table(name = "post")
public class Post extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String descriptions;

    @Column(length = EntityProperties.LENGTH_ID)
    private String teacherId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String classId;
}
