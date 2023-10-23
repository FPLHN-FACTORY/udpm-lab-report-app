package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdTemplateMemberFactoryResponse extends IsIdentified {

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.id_member_team_factory}")
    String getIdMemberTeamFactory();
}
