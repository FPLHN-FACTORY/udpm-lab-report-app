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

/**
 * @author thangncph26123
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "role_member_project")
@AllArgsConstructor
@NoArgsConstructor
public class RoleMemberProject extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String memberProjectId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String roleProjectId;
}