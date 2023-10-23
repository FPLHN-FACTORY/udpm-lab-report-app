package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.labreport.infrastructure.constant.RoleDefault;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdDetailProjectCateMemberRespone;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberAndRoleProjectCustomResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdProjectReponse;
import com.labreportapp.portalprojects.core.admin.repository.AdLabelProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleConfigRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsRoleProjectRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectCategoryRepository;
import com.labreportapp.portalprojects.core.admin.repository.AdProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdMemberProjectService;
import com.labreportapp.portalprojects.core.admin.service.AdProjectService;
import com.labreportapp.portalprojects.core.common.base.PageableObject;
import com.labreportapp.portalprojects.entity.Label;
import com.labreportapp.portalprojects.entity.LabelProject;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.ProjectCategory;
import com.labreportapp.portalprojects.entity.RoleConfig;
import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.entity.RoleProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusProject;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.constant.TypeProject;
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
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private AdPotalsRoleConfigRepository adPotalsRoleConfigRepository;

    @Autowired
    private AdPotalsRoleProjectRepository adPotalsRoleProjectRepository;

    @Autowired
    private AdPotalsRoleMemberProjectRepository adPotalsRoleMemberProjectRepository;

    @Autowired
    private AdMemberProjectService adMemberProjectService;

    @Override
    public List<Project> findAllProject(Pageable pageable) {
        return adProjectRepository.findAllProject(pageable);
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public AdProjectReponse createProject(@Valid AdCreateProjectRequest request) {
        String checkCode = adProjectRepository.getProjectByCode(request.getCode());
        if (checkCode != null) {
            throw new RestApiException(Message.CODE_PROJECT_ALREADY_EXISTS);
        }
        if (request.getStartTime() != null && request.getEndTime() != null) {
            if (request.getEndTime() - request.getStartTime() <= 0) {
                throw new RestApiException(Message.START_TIME_MORE_THAN_END_TIME);
            }
        } else if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new RestApiException(Message.TIME_NOT_NULL);
        }
        List<AdCreateMemberProjectRequest> listMember = request.getListMembers();

        if (listMember.size() > 0) {
            AtomicBoolean checkRoleDefault = new AtomicBoolean(false);
            listMember.forEach(m -> {
                m.getRole().forEach(i -> {
                    if (i.equals("Không có vai trò mặc định, vui lòng chọn vai trò khác")) {
                        checkRoleDefault.set(true);
                    }
                });
            });
            if (checkRoleDefault.get()) {
                throw new RestApiException(Message.ROLE_DEFAULT_NOT_EMPTY);
            }
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
        project.setTypeProject(TypeProject.DU_AN_XUONG_DU_AN);
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


        List<MemberProject> newMemberProject = new ArrayList<>();
        listMember.forEach(item -> {
            MemberProject memberProject = new MemberProject();
            memberProject.setProjectId(newProject.getId());
            memberProject.setMemberId(item.getMemberId());
            memberProject.setEmail(item.getEmail());
            memberProject.setStatusWork(StatusWork.DANG_LAM);
            newMemberProject.add(memberProject);
        });

        List<RoleConfig> listRoleConfig = adPotalsRoleConfigRepository.findAll();
        List<RoleProject> newRoleProject = new ArrayList<>();
        listRoleConfig.forEach(item -> {
            RoleProject roleProject = new RoleProject();
            roleProject.setProjectId(newProject.getId());
            roleProject.setName(item.getName());
            roleProject.setDescription(item.getDescription());
            roleProject.setRoleDefault(item.getRoleDefault());
            newRoleProject.add(roleProject);
        });
        List<RoleProject> listRoleProject = adPotalsRoleProjectRepository.saveAll(newRoleProject);

        List<RoleMemberProject> newRoleMemberProject = new ArrayList<>();
        Optional<AdCreateMemberProjectRequest> memberDefault = listMember.stream()
                .filter(i -> i.getMemberId().equals(labReportAppSession.getUserId()))
                .findFirst();
        if (!memberDefault.isPresent()){
            MemberProject memberCreateProject = new MemberProject();
            memberCreateProject.setMemberId(labReportAppSession.getUserId());
            memberCreateProject.setProjectId(newProject.getId());
            memberCreateProject.setEmail(labReportAppSession.getEmail());
            memberCreateProject.setStatusWork(StatusWork.DANG_LAM);
            memberCreateProject.setId(adMemberProjectRepository.save(memberCreateProject).getId());

            RoleMemberProject roleMemberProjectDefault = new RoleMemberProject();
            roleMemberProjectDefault.setMemberProjectId(memberCreateProject.getId());
            Optional<RoleProject> roleProSetDefault = listRoleProject.stream()
                    .filter(i -> i.getRoleDefault().equals(RoleDefault.DEFAULT))
                    .findFirst();

            if (roleProSetDefault.isPresent()) {
                roleMemberProjectDefault.setRoleProjectId(roleProSetDefault.get().getId());
            } else {
                if (listRoleProject.size() > 0) {
                    RoleProject roleProjectAp = listRoleProject.get(0);
                    roleMemberProjectDefault.setRoleProjectId(roleProjectAp.getId());
                }
            }
            newRoleMemberProject.add(roleMemberProjectDefault);
        }
        List<MemberProject> listMemberProject = adMemberProjectRepository.saveAll(newMemberProject);
        listMember.forEach(member -> {
            member.getRole().forEach(role -> {
                listRoleProject.forEach(roleProject -> {
                    if (roleProject.getName().equals(role)) {
                        RoleMemberProject roleMemberProject = new RoleMemberProject();
                        roleMemberProject.setRoleProjectId(roleProject.getId());
                        listMemberProject.forEach(memberProject -> {
                            if (member.getMemberId().equals(memberProject.getMemberId())) {
                                roleMemberProject.setMemberProjectId(memberProject.getId());
                            }
                        });
                        newRoleMemberProject.add(roleMemberProject);
                    }
                });

            });
        });
        adPotalsRoleMemberProjectRepository.saveAll(newRoleMemberProject);
        Optional<AdProjectReponse> projectView = adProjectRepository.findOneProjectById(newProject.getId());
        return projectView.get();
    }


    @Override
    public AdDetailProjectCateMemberRespone detailUpdate(String idProject) {
        Optional<AdProjectReponse> projectFind = adProjectRepository.findOneProjectById(idProject);
        if (!projectFind.isPresent()) {
            return null;
        }
        List<AdMemberAndRoleProjectCustomResponse> listMemberRole = adMemberProjectService
                .findAllMemberJoinProject(idProject);
        List<ProjectCategory> listProjectCategory = adProjectCategoryRepository.findAllByProjectId(idProject);
        AdProjectReponse project = projectFind.get();
        AdDetailProjectCateMemberRespone objReturn = new AdDetailProjectCateMemberRespone();
        objReturn.setCode(project.getCode());
        objReturn.setName(project.getName());
        objReturn.setBackGroundImage(project.getBackGroundImage());
        objReturn.setBackGroundColor(project.getBackGroundColor());
        objReturn.setStartTime(project.getStartTime());
        objReturn.setEndTime(project.getEndTime());
        objReturn.setProgress(project.getProgress());
        objReturn.setStatusProject(project.getStatusProject());
        objReturn.setDescriptions(project.getDescriptions());
        objReturn.setListMemberRole(listMemberRole);
        objReturn.setListCategory(listProjectCategory);
        return objReturn;
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
                memberProjectFind.setStatusWork(StatusWork.DANG_LAM);
                adMemberProjectRepository.save(memberProjectFind);
            } else {
                MemberProject memberProject = new MemberProject();
                memberProject.setProjectId(project.getId());
                memberProject.setMemberId(item.getMemberId());
                memberProject.setEmail(item.getEmail());
                memberProject.setStatusWork(StatusWork.DANG_LAM);
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
        Page<AdProjectReponse> reponses = adProjectRepository.findProjectPage(rep, pageable);
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
