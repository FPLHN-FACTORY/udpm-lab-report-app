package com.labreportapp.portalprojects.entity;

import com.labreportapp.portalprojects.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
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
@Entity
@Getter
@Setter
@ToString
@Table(name = "todo_list")
@AllArgsConstructor
@NoArgsConstructor
public class TodoList extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE, nullable = false)
    private String code;

    @Column(length = EntityProperties.LENGTH_NAME)
    @Nationalized
    private String name;

    @Column(nullable = false)
    @Index(name = "idx_todo_list_id")
    private Byte indexTodoList;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_project_id")
    private String projectId;
}
