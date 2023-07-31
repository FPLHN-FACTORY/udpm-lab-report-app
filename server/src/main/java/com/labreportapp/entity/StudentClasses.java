package com.labreportapp.entity;

import com.labreportapp.entity.base.PrimaryEntity;
import com.labreportapp.infrastructure.constant.EntityProperties;
import com.labreportapp.infrastructure.constant.RoleTeam;
import com.labreportapp.infrastructure.constant.StatusTeam;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Index;

/**
 * @author thangncph26123
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "student_classes")
public class StudentClasses extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "index_student_classes_student_id")
    private String studentId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "index_student_classes_class_id")
    private String classId;

    @Column(length = EntityProperties.LENGTH_ID)
    @Index(name = "index_student_classes_team_id")
    private String teamId;

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String email;

    @Column(nullable = false)
    private RoleTeam role;

    @Column(nullable = false)
    private StatusTeam status;
}
