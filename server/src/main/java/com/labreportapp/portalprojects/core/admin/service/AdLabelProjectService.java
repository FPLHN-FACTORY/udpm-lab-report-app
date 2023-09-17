package com.labreportapp.portalprojects.core.admin.service;

import com.labreportapp.portalprojects.core.admin.model.request.AdCrearteLabelProjectRequest;
import com.labreportapp.portalprojects.entity.LabelProject;

import java.util.List;

/**
 * @author NguyenVinh
 */
public interface AdLabelProjectService {

    List<LabelProject> addAllLabelProject (final List<AdCrearteLabelProjectRequest> command);

}
