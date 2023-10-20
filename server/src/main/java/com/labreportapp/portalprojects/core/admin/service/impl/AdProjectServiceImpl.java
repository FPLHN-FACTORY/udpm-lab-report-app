package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.portalprojects.core.admin.model.request.AdCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.admin.repository.AdLabelProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.*;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.RoleMemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.repository.LabelRepository;
import com.labreportapp.portalprojects.util.FormUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author hieundph25894
 */
@Service
@Validated
public class AdProjectServiceImpl implements AdProjectService {

    @Autowired
    private AdProjectRepository adProjectRepository;

    @Autowired
    @Qualifier(LabelRepository.NAME)
    private LabelRepository labelRepository;

    @Autowired
    private AdLabelProjectRepository adLabelProjectRepository;

    @Autowired
    private AdProjectCategoryRepository adProjectCategoryRepository;

    @Autowired
    private AdMemberProjectRepository adMemberProjectRepository;

    private FormUtils formUtils = new FormUtils();

    private List<AdProjectReponse> listProject;

    @Override
    public List<Project> findAllProject(Pageable pageable) {
        return adProjectRepository.findAllProject(pageable);
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public Project createProject(@Valid AdCreateProjectRequest request) {
        String checkCode = adProjectRepository.getProjectByCode(request.getCode());
        if (checkCode != null) {
            throw new RestApiException(Message.CODE_PROJECT_ALREADY_EXISTS);
        }
        Project project = formUtils.convertToObject(Project.class, request);
        project.setStartTime(request.getStartTime());
        project.setEndTime(request.getEndTime());
        Long currentTime = new Date().getTime();
        if (currentTime < request.getStartTime()) {
            project.setStatusProject(StatusProject.CHUA_DIEN_RA);
        }
        if (currentTime >= request.getStartTime() && currentTime <= request.getEndTime()) {
            project.setStatusProject(StatusProject.DANG_DIEN_RA);
        }
        if (request.getEndTime() < currentTime) {
            project.setStatusProject(StatusProject.DA_DIEN_RA);
        }
        project.setProgress(Float.parseFloat("0"));
        project.setBackgroundColor("#59a1e3");
        Project newProject = adProjectRepository.save(project);
        List<Label> listLabel = labelRepository.findAll();
        List<LabelProject> newLabelProject = new ArrayList<>();
        listLabel.forEach(item -> {
            LabelProject labelProject = new LabelProject();
            labelProject.setProjectId(newProject.getId());
            labelProject.setColorLabel(item.getColorLabel());
            labelProject.setCode(item.getCode());
            labelProject.setName(item.getName());
            newLabelProject.add(labelProject);
        });
        adLabelProjectRepository.saveAll(newLabelProject);
        List<String> listCaregoryIds = request.getListCategorysId();
        List<ProjectCategory> newCategoryProject = new ArrayList<>();
        listCaregoryIds.forEach(item -> {
            ProjectCategory categoryProject = new ProjectCategory();
            categoryProject.setCategoryId(item);
            categoryProject.setProjectId(newProject.getId());
            newCategoryProject.add(categoryProject);
        });
        adProjectCategoryRepository.saveAll(newCategoryProject);
        List<AdCreateMemberProjectRequest> listMember = request.getListMembers();
        List<MemberProject> newMemberProject = new ArrayList<>();
        listMember.forEach(item -> {
            MemberProject memberProject = new MemberProject();
            memberProject.setProjectId(newProject.getId());
            memberProject.setMemberId(item.getMemberId());
            memberProject.setEmail(item.getEmail());
            if (item.getRole().equals("0") || item.getRole() == "0") {
                memberProject.setRole(RoleMemberProject.MANAGER);
            } else if (item.getRole().equals("1") || item.getRole() == "1") {
                memberProject.setRole(RoleMemberProject.LEADER);
            } else if (item.getRole().equals("2") || item.getRole() == "2") {
                memberProject.setRole(RoleMemberProject.DEV);
            } else {
                memberProject.setRole(RoleMemberProject.TESTER);
            }
            System.out.println(memberProject.getRole());
            if (item.getStatusWork().equals("0")) {
                memberProject.setStatusWork(StatusWork.DANG_LAM);
            } else {
                memberProject.setStatusWork(StatusWork.DA_NGHI_VIEC);
            }
            newMemberProject.add(memberProject);
        });
        adMemberProjectRepository.saveAll(newMemberProject);
        return newProject;
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public Project updateProject(@Valid AdUpdateProjectRequest request) {
        Optional<Project> projectCheck = adProjectRepository.findById(request.getId());
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        String checkCodeProject = adProjectRepository.findByIdCode(request.getCode(), request.getId());
        Project project = projectCheck.get();
        if (checkCodeProject != null && !project.getCode().equals(request.getCode())) {
            throw new RestApiException(Message.CODE_PROJECT_ALREADY_EXISTS);
        }
        project.setCode(request.getCode());
        project.setDescriptions(request.getDescriptions());
        project.setName(request.getName());
        project.setStartTime(request.getStartTime());
        project.setEndTime(request.getEndTime());
        Long currentTime = new Date().getTime();
        if (currentTime < request.getStartTime()) {
            project.setStatusProject(StatusProject.CHUA_DIEN_RA);
        }
        if (currentTime >= request.getStartTime() && currentTime <= request.getEndTime()) {
            project.setStatusProject(StatusProject.DANG_DIEN_RA);
        }
        if (request.getEndTime() < currentTime) {
            project.setStatusProject(StatusProject.DA_DIEN_RA);
        }
        List<String> listCaregoryIds = request.getListCategorysId();
        List<ProjectCategory> newCategoryProject = new ArrayList<>();
        listCaregoryIds.forEach(item -> {
            ProjectCategory categoryProject = new ProjectCategory();
            categoryProject.setCategoryId(item);
            categoryProject.setProjectId(project.getId());
            newCategoryProject.add(categoryProject);
        });
        adProjectCategoryRepository.saveAll(newCategoryProject);
        List<AdCreateMemberProjectRequest> listMember = request.getListMembers();
        List<MemberProject> newMemberProject = new ArrayList<>();
        listMember.forEach(item -> {
            MemberProject memberProjectFind = adMemberProjectRepository.findMemberProject(item.getMemberId(), item.getProjectId());
            if (memberProjectFind != null) {
                memberProjectFind.setEmail(item.getEmail());
                if (item.getRole() == "0" || item.getRole().equals("0")) {
                    memberProjectFind.setRole(RoleMemberProject.MANAGER);
                } else if (item.getRole() == "1" || item.getRole().equals("1")) {
                    memberProjectFind.setRole(RoleMemberProject.LEADER);
                } else if (item.getRole() == "2" || item.getRole().equals("2")) {
                    memberProjectFind.setRole(RoleMemberProject.DEV);
                } else {
                    memberProjectFind.setRole(RoleMemberProject.TESTER);
                }
                if (item.getStatusWork() == "0" || item.getStatusWork().equals("0")) {
                    memberProjectFind.setStatusWork(StatusWork.DANG_LAM);
                } else {
                    memberProjectFind.setStatusWork(StatusWork.DA_NGHI_VIEC);
                }
                adMemberProjectRepository.save(memberProjectFind);
            } else {
                MemberProject memberProject = new MemberProject();
                memberProject.setProjectId(project.getId());
                memberProject.setMemberId(item.getMemberId());
                memberProject.setEmail(item.getEmail());
                if (item.getRole() == "0" || item.getRole().equals("0")) {
                    memberProject.setRole(RoleMemberProject.MANAGER);
                } else if (item.getRole() == "1" || item.getRole().equals("1")) {
                    memberProject.setRole(RoleMemberProject.LEADER);
                } else if (item.getRole() == "2" || item.getRole().equals("2")) {
                    memberProject.setRole(RoleMemberProject.DEV);
                } else {
                    memberProject.setRole(RoleMemberProject.TESTER);
                }
                if (item.getStatusWork() == "0" || item.getStatusWork().equals("0")) {
                    memberProject.setStatusWork(StatusWork.DANG_LAM);
                } else {
                    memberProject.setStatusWork(StatusWork.DA_NGHI_VIEC);
                }
                newMemberProject.add(memberProject);
            }
        });
        adMemberProjectRepository.saveAll(newMemberProject);
        return adProjectRepository.save(project);
    }

    @Override
    public PageableObject<AdProjectReponse> searchProject(final AdFindProjectRequest rep) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = null;
        Date endTime = null;
        if (rep.getStartTime() != null && !rep.getStartTime().equals("null")
                && !rep.getStartTime().equals("undefined") && !rep.getStartTime().contains("Invalid")) {
            try {
                startTime = sdf.parse(rep.getStartTime());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RestApiException(Message.INVALID_DATE);
            }
            Long startTimeLong = startTime.getTime();
            rep.setStartTimeLong(startTimeLong);
        }
        if (rep.getEndTime() != null && !rep.getEndTime().equals("null")
                && !rep.getEndTime().equals("undefined") && !rep.getEndTime().contains("Invalid")) {
            try {
                endTime = sdf.parse(rep.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RestApiException(Message.INVALID_DATE);
            }
            Long endTimeLong = endTime.getTime();
            rep.setEndTimeLong(endTimeLong);
        }
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdProjectReponse> reponses = adProjectRepository.findByNameProject(rep, pageable);
        listProject = reponses.stream().toList();
        return new PageableObject<>(reponses);
    }

    @Override
    public Project findProjectById(String id) {
        Optional<Project> projectCheck = adProjectRepository.findById(id);
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        return projectCheck.get();
    }

    @Override
    public Boolean removeProject(String id) {
        Optional<Project> projectCheck = adProjectRepository.findById(id);
        if (!projectCheck.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        adProjectRepository.delete(projectCheck.get());
        return true;
    }

}
