package com.labreportapp.labreport.core.teacher.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author quynhncph26201
 */
public interface TeTemplateMemberFactoryResponse extends IsIdentified {

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.id_member_team_factory}")
    String getIdMemberTeamFactory();

}
