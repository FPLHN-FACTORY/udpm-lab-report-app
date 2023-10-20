package com.labreportapp.labreport.core.admin.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdMemberFactoryResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.member_id}")
    String getMemberId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.role_member_factory}")
    String getRoleMemberFactory();

    @Value("#{target.number_team}")
    Integer getNumberTeam();

    @Value("#{target.status_member_factory}")
    Integer getStatusMemberFactory();

}
