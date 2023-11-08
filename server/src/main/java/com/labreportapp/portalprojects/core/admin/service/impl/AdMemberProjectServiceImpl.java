package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.portalprojects.core.admin.model.request.AdCreateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdFindProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.request.AdUpdateMemberProjectRequest;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberAndRoleProjectCustomResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberAndRoleProjectResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberProjectReponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdMemberRoleBaseResponse;
import com.labreportapp.portalprojects.core.admin.model.response.AdRoleMemberProjectDetailResponse;
import com.labreportapp.portalprojects.core.admin.repository.AdMemberProjectRepository;
import com.labreportapp.portalprojects.core.admin.service.AdMemberProjectService;
import com.labreportapp.portalprojects.core.admin.service.AdPotalsRoleMemberProjectService;
import com.labreportapp.portalprojects.entity.MemberProject;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import com.labreportapp.portalprojects.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author NguyenVinh
 */
@Service
public class AdMemberProjectServiceImpl implements AdMemberProjectService {

    @Autowired
    private AdMemberProjectRepository adMemberProjectRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdPotalsRoleMemberProjectService adPotalsRoleMemberProjectService;

    private FormUtils formUtils = new FormUtils();

    @Override
    public List<AdMemberProjectReponse> searchProject(AdFindProjectRequest rep) {
        return adMemberProjectRepository.findByName(rep);
    }

    @Override
    public List<AdMemberAndRoleProjectCustomResponse> findAllMemberJoinProject(String idProject) {
        List<AdMemberAndRoleProjectResponse> listMemberId = adMemberProjectRepository.findAllMemberJoinProject(idProject);
        List<String> idList = listMemberId.stream()
                .map(AdMemberAndRoleProjectResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<AdMemberAndRoleProjectCustomResponse> listReturn = new ArrayList<>();
        if (listMemberId.size() == 0) {
            return listReturn;
        }
        List<AdRoleMemberProjectDetailResponse> listMemberRole = adPotalsRoleMemberProjectService
                .getAllRoleMemberProjByIdProj(idProject);
        List<SimpleResponse> listCall = callApiIdentity.handleCallApiGetListUserByListId(idList);
        listMemberId.forEach(db -> {
            listCall.forEach(call -> {
                if (db.getMemberId().equals(call.getId())) {
                    AdMemberAndRoleProjectCustomResponse obj = new AdMemberAndRoleProjectCustomResponse();
                    obj.setStt(db.getStt());
                    obj.setEmail(db.getEmail());
                    List<AdMemberRoleBaseResponse> listRole = new ArrayList<>();
                    obj.setStatus(db.getStatus());
                    obj.setMemberId(db.getMemberId());
                    obj.setName(call.getName());
                    obj.setUserName(call.getUserName());
                    obj.setPicture(call.getPicture());
                    obj.setIdMemberProject(db.getIdMemberProject());
                    listMemberRole.forEach(role -> {
                        if (db.getMemberId().equals(role.getMemberId())) {
                            AdMemberRoleBaseResponse roleDetail = new AdMemberRoleBaseResponse();
                            roleDetail.setIdRoleMemberProject(role.getIdRoleMemberProject());
                            roleDetail.setIdRole(role.getRoleProjectId());
                            roleDetail.setNameRole(role.getNameRole());
                            listRole.add(roleDetail);
                        }
                    });
                    obj.setListRole(listRole);
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public List<MemberProject> getAll() {
        return adMemberProjectRepository.findAll();
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public MemberProject createMemberProject(AdCreateMemberProjectRequest command) {
        AdMemberProjectReponse reponse = adMemberProjectRepository.getOne(command.getProjectId(), command.getMemberId());
        if (reponse != null) {
            throw new RestApiException(Message.CODE_MENBER_PROJECT_ALREADY_EXISTS);
        }
        MemberProject memberProject = formUtils.convertToObject(MemberProject.class, command);
        return adMemberProjectRepository.save(memberProject);
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public List<MemberProject> createListMemberProject(List<AdCreateMemberProjectRequest> list) {
        List<MemberProject> listAdd = new ArrayList<>();
        list.stream().filter(a -> adMemberProjectRepository.getOne(a.getProjectId(), a.getMemberId()) == null)
                .forEach(a -> listAdd.add(formUtils.convertToObject(MemberProject.class, a)));
        return adMemberProjectRepository.saveAll(listAdd);
    }

    @Override
    @CacheEvict(value = {"membersByProject"}, allEntries = true)
    public MemberProject updateMemberProject(AdUpdateMemberProjectRequest command) {
        Optional<MemberProject> optional = adMemberProjectRepository.findById(command.getId());
        if (!optional.isPresent()) {
            throw new RestApiException(Message.MEMBER_PROJECT_NOT_EXISTS);
        }
        return optional.get();
    }

    @Override
    public AdMemberProjectReponse getOne(final String idMember, final String idProject) {
        AdMemberProjectReponse reponse = adMemberProjectRepository.getOne(idProject, idMember);
        return reponse;
    }

    @Override
    public Boolean delete(String id) {
        Optional<MemberProject> optional = adMemberProjectRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RestApiException(Message.MEMBER_PROJECT_NOT_EXISTS);
        }
        adMemberProjectRepository.delete(optional.get());
        return true;
    }
}
