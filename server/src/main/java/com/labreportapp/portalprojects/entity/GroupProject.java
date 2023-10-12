package com.labreportapp.portalprojects.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
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
@Entity
@Getter
@Setter
@ToString
@Table(name = "group_project")
@AllArgsConstructor
@NoArgsConstructor
public class GroupProject extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    @Nationalized
    private String name;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String description;

    @Column(length = EntityProperties.LENGTH_DESCRIPTION)
    private String backgroundImage;
}
