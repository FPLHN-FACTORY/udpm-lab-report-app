package com.labreportapp.labreport.core.teacher.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author hieundph25894
 */
public interface TeDetailTeamReportRespone {

    @Value("#{target. id_team}")
    String getIdTeam();

    @Value("#{target.name_team}")
    String getNameTeam();

    @Value("#{target.id_report}")
    String getIdReport();

}
