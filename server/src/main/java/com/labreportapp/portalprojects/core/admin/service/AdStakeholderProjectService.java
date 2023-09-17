package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateStakeholderRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectStkResponse;
import com.labreportapp.portalprojects.entity.StakeholderProject;
import jakarta.validation.Valid;
import java.util.List;

/**
 * @author quynhncph26201
 */

public interface AdStakeholderProjectService {

    List<AdProjectStkResponse> getProjectsByStakeholderId(final String stakeholderId);

    List<AdProjectStkResponse> getAllProjects();

    List<StakeholderProject> updateStakeHolder(@Valid AdUpdateStakeholderRequest comand);

}
