package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdExcelMemberFactoryResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.role_member_factory}")
    String getRoleMemberFactory();

    @Value("#{target.member_team_factory}")
    String getMemberTeamFactory();

    @Value("#{target.status_member_factory}")
    Integer getStatusMemberFactory();

}
