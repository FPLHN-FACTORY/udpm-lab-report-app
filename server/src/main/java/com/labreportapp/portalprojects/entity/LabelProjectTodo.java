package com.labreportapp.portalprojects.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;

/**
 * @author thangncph26123
 */

@Entity
@Getter
@Setter
@ToString
@Table(name = "label_project_todo")
@AllArgsConstructor
@NoArgsConstructor
public class LabelProjectTodo extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String labelProjectId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String todoId;
}
