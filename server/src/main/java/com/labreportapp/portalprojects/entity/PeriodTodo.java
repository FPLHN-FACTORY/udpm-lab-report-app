package com.labreportapp.portalprojects.entity;

import com.labreportapp.portalprojects.entity.base.PrimaryEntity;
import com.labreportapp.portalprojects.infrastructure.constant.EntityProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import org.hibernate.annotations.Index;

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "period_todo")
@AllArgsConstructor
@NoArgsConstructor
public class PeriodTodo extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_periodId")
    private String periodId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "idx_todoId")
    private String todoId;
}
