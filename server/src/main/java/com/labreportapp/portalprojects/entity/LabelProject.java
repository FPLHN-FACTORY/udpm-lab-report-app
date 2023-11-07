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
@Table(name = "label_project")
@AllArgsConstructor
@NoArgsConstructor
public class LabelProject extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String projectId;

    @Column(length = EntityProperties.LENGTH_NAME_SHORT)
    @Nationalized
    private String name;

    @Column(nullable = false)
    private String colorLabel;
}
