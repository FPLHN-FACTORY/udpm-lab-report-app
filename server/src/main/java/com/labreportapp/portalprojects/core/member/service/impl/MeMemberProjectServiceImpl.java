package com.labreportapp.portalprojects.core.member.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.core.member.model.request.MeCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeListMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.request.MeMemberProjectCustom;
import com.labreportapp.portalprojects.core.member.model.request.MeUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.core.member.model.response.MeMemberProjectResponse;
import com.labreportapp.portalprojects.core.member.repository.MeMemberProjectRepository;
import com.labreportapp.portalprojects.core.member.repository.MeProjectRepository;
import com.labreportapp.portalprojects.core.member.service.MeMemberProjectService;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.entity.Project;
import com.labreportapp.portalprojects.entity.RoleMemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.constant.StatusWork;
import com.labreportapp.portalprojects.infrastructure.constant.TypeProject;
import com.labreportapp.portalprojects.infrastructure.exception.rest.MessageHandlingException;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.infrastructure.successnotification.ConstantMessageSuccess;
import com.labreportapp.portalprojects.infrastructure.successnotification.SuccessNotificationSender;
import com.labreportapp.portalprojects.repository.RoleMemberProjectRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class MeMemberProjectServiceImpl implements MeMemberProjectService {

    @Autowired
    private MeMemberProjectRepository meMemberProjectRepository;

    @Autowired
    private SuccessNotificationSender successNotificationSender;

    @Autowired
    private MeProjectRepository meProjectRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private RoleMemberProjectRepository roleMemberProjectRepository;

    @Override
    @Cacheable(value = "membersByProject", key = "#idProject")
    public List<MeMemberProjectCustom> getAllMemberProject(String idProject) {
        List<MeMemberProjectResponse> listMemberProject = meMemberProjectRepository.getAllMemberProject(idProject);
        List<String> memberIds = listMemberProject.stream()
                .map(MeMemberProjectResponse::getMemberId)
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(memberIds);
        List<MeMemberProjectCustom> listCustom = new ArrayList<>();
        listMemberProject.forEach(memberProject -> {
            listResponse.forEach(response -> {
                if (memberProject.getMemberId().equals(response.getId())) {
                    MeMemberProjectCustom custom = new MeMemberProjectCustom();
                    custom.setId(memberProject.getId());
                    custom.setMemberId(response.getId());
                    custom.setEmail(response.getEmail());
                    custom.setName(response.getName());
                    custom.setPicture(response.getPicture());
                    custom.setUserName(response.getUserName());
                    List<String> roles = meMemberProjectRepository.getAllRoleProjectByIdMemberProject(memberProject.getId());
                    custom.setRoles(roles);
                    custom.setStatusWork(memberProject.getStatusWork());
                    listCustom.add(custom);
                }
            });
        });
        return listCustom;
    }

    @Override
    public List<SimpleResponse> getAllMemberTeam(String idProject) {
        Optional<Project> projectFind = meProjectRepository.findById(idProject);
        if (!projectFind.isPresent()) {
            throw new RestApiException(Message.PROJECT_NOT_EXISTS);
        }
        List<String> listId = new ArrayList<>();
        if(projectFind.get().getTypeProject() == TypeProject.DU_AN_XUONG_THUC_HANH) {
            listId = meMemberProjectRepository.getAllMemberTeam(idProject);
        } else {
            listId = meMemberProjectRepository.getAllMemberFactory();
        }
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(listId);
        return listResponse;
    }

    @Override
    @Transactional
    @Synchronized
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public MemberProject update(@Valid MeUpdateMemberProjectRequest request, StompHeaderAccessor headerAccessor) {
        try {
            MemberProject memberProject = meMemberProjectRepository.findMemberProject(request.getIdProject(), request.getIdMember());
            if (memberProject == null) {
                throw new MessageHandlingException(Message.MEMBER_PROJECT_NOT_EXISTS);
            }
            memberProject.setStatusWork(StatusWork.values()[request.getStatusWork()]);
            meMemberProjectRepository.deleteRoleMemberProject(memberProject.getId());
            List<com.labreportapp.portalprojects.entity.RoleMemberProject> roleMemberProjects = new ArrayList<>();
            request.getRole().forEach(role -> {
                com.labreportapp.portalprojects.entity.RoleMemberProject roleMemberProject = new com.labreportapp.portalprojects.entity.RoleMemberProject();
                roleMemberProject.setMemberProjectId(memberProject.getId());
                roleMemberProject.setRoleProjectId(role);
                roleMemberProjects.add(roleMemberProject);
            });
            roleMemberProjectRepository.saveAll(roleMemberProjects);
            successNotificationSender.senderNotification(ConstantMessageSuccess.CAP_NHAT_THANH_CONG, headerAccessor);
            return meMemberProjectRepository.save(memberProject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Synchronized
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public List<MemberProject> create(@Valid MeListMemberProjectRequest request, StompHeaderAccessor headerAccessor) {
        Optional<Project> projectFind = meProjectRepository.findById(request.getListMemberProject().get(0).getProjectId());
        if (!projectFind.isPresent()) {
            throw new MessageHandlingException(Message.PROJECT_NOT_EXISTS);
        }
        List<MemberProject> listMemberProject = new ArrayList<>();
        for (MeCreateMemberProjectRequest xx : request.getListMemberProject()) {
            MemberProject memberProject = new MemberProject();
            memberProject.setMemberId(xx.getMemberId());
            memberProject.setProjectId(xx.getProjectId());
            memberProject.setEmail(xx.getEmail());
            memberProject.setStatusWork(StatusWork.DANG_LAM);
            listMemberProject.add(memberProject);
        }
        List<MemberProject> listMemberProjectNew = meMemberProjectRepository.saveAll(listMemberProject);
        List<RoleMemberProject> roleMemberProjects = new ArrayList<>();
        listMemberProjectNew.forEach(memberProject -> {
            request.getListMemberProject().forEach(xx -> {
                if(memberProject.getMemberId().equals(xx.getMemberId())) {
                    xx.getRole().forEach(role -> {
                        RoleMemberProject roleMemberProject = new RoleMemberProject();
                        roleMemberProject.setMemberProjectId(memberProject.getId());
                        roleMemberProject.setRoleProjectId(role);
                        roleMemberProjects.add(roleMemberProject);
                    });
                }
            });
        });
        roleMemberProjectRepository.saveAll(roleMemberProjects);
        successNotificationSender.senderNotification(ConstantMessageSuccess.THEM_THANH_CONG, headerAccessor);
        return listMemberProject;
    }
}
