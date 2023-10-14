package com.labreportapp.labreport.entity;

import com.labreportapp.labreport.entity.base.PrimaryEntity;
import com.labreportapp.labreport.infrastructure.constant.EntityProperties;
import com.labreportapp.labreport.infrastructure.constant.StatusMemberTeamFactory;
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
@Table(name = "member_team_factory")
public class MemberTeamFactory extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_ID)
    private String teamFactoryId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String memberId;

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String email;

    @Column(length = EntityProperties.LENGTH_ID)
    private String roleFactoryId;

    @Column
    private StatusMemberTeamFactory statusMemberTeamFactory;
}
