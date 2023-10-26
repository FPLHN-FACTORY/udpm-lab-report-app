package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateGroupProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdDetailGroupProjectResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdGroupProjectResponse;
import com.labreportapp.portalprojects.core.admin.repository.AdGroupProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdGroupProjectService;
import com.labreportapp.portalprojects.entity.GroupProject;
import com.labreportapp.portalprojects.infrastructure.cloudinary.CloudinaryUploadImages;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdGroupProjectServiceImpl implements AdGroupProjectService {

    @Autowired
    private AdGroupProjectRepository adGroupProjectRepository;

    @Autowired
    private CloudinaryUploadImages cloudinaryUploadImages;

    @Override
    public PageableObject<AdGroupProjectResponse> getAllPage(final AdFindGroupProjectRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(adGroupProjectRepository.getAllPage(pageable, request));
    }

    @Override
    @Transactional
    public AdGroupProjectResponse updateGroupProject(@Valid AdUpdateGroupProjectRequest request) throws IOException {
        Optional<GroupProject> groupProjectFind = adGroupProjectRepository.findById(request.getId());
        if (!groupProjectFind.isPresent()) {
            throw new RestApiException(Message.GROUP_PROJECT_NOT_EXISTS);
        }
        groupProjectFind.get().setName(request.getName());
        groupProjectFind.get().setDescription(request.getDescriptions());
        if (request.getFile() != null) {
            groupProjectFind.get().setBackgroundImage(cloudinaryUploadImages.uploadImage(request.getFile()));
        }
        adGroupProjectRepository.save(groupProjectFind.get());
        AdGroupProjectResponse adGroupProjectResponse = adGroupProjectRepository.findGroupProjectById(request.getId());
        return adGroupProjectResponse;
    }

    @Override
    public AdGroupProjectResponse createGroupProject(AdCreateGroupProjectRequest request) throws IOException {
        GroupProject groupProject = new GroupProject();
        groupProject.setName(request.getName());
        groupProject.setDescription(request.getDescriptions());
        if (request.getFile() != null) {
            groupProject.setBackgroundImage(cloudinaryUploadImages.uploadImage(request.getFile()));
        }
        GroupProject groupProjectNew = adGroupProjectRepository.save(groupProject);
        AdGroupProjectResponse adGroupProjectResponse = adGroupProjectRepository.findGroupProjectById(groupProjectNew.getId());
        return adGroupProjectResponse;
    }

    @Override
    public AdGroupProjectResponse detailGroupProject(String id) {
        return adGroupProjectRepository.findGroupProjectById(id);
    }

    @Override
    public List<AdDetailGroupProjectResponse> getAllProject(String id) {
        return adGroupProjectRepository.getAllProject(id);
    }

    @Override
    public List<GroupProject> getAllGroupToProjectManagement() {
        return adGroupProjectRepository.findAll();
    }
}
