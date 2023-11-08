package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.request.AdCreateMembersInTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdCreateTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdDeleteListMemberTeamFactoryRequest;
import com.labreportapp.labreport.core.admin.model.request.AdFindTeamRequest;
import com.labreportapp.labreport.core.admin.model.request.AdUpdateTeamRequest;
import com.labreportapp.labreport.core.admin.model.response.AdAllMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.model.response.AdTeamFactoryCustom;
import com.labreportapp.labreport.core.admin.model.response.AdTeamResponse;
import com.labreportapp.labreport.core.admin.model.response.AdTemplateMemberFactoryResponse;
import com.labreportapp.labreport.core.admin.repository.AdMemberFactoryRepository;
import com.labreportapp.labreport.core.admin.repository.AdTeamRepository;
import com.labreportapp.labreport.core.admin.service.AdTeamService;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.entity.MemberTeamFactory;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.repository.MemberTeamFactoryRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
import com.labreportapp.labreport.util.FormUtils;
import com.labreportapp.portalprojects.infrastructure.constant.Message;
import com.labreportapp.portalprojects.infrastructure.exception.rest.RestApiException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class AdTeamServiceImpl implements AdTeamService {

    @Autowired
    private AdTeamRepository adTeamRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    private AdMemberFactoryRepository adMemberFactoryRepository;

    @Autowired
    @Qualifier(MemberTeamFactoryRepository.NAME)
    private MemberTeamFactoryRepository memberTeamFactoryRepository;

    @Override
    public List<TeamFactory> findAllTeam(Pageable pageable) {
        return adTeamRepository.getAllTeam(pageable);
    }

    @Override
    public TeamFactory createTeam(AdCreateTeamRequest obj) {
        TeamFactory team = formUtils.convertToObject(TeamFactory.class, obj);
        return adTeamRepository.save(team);
    }

    @Override
    public TeamFactory updateTeam(AdUpdateTeamRequest obj) {
        Optional<TeamFactory> findById = adTeamRepository.findById(obj.getId());
        if (!findById.isPresent()) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        TeamFactory team = findById.get();
        team.setName(obj.getName());
        team.setDescriptions(obj.getDescriptions());

        return adTeamRepository.save(team);
    }

    @Override
    public PageableObject<AdTeamFactoryCustom> searchTeam(AdFindTeamRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<AdTeamResponse> adTeamResponses = adTeamRepository.searchTeam(rep, pageable);
        List<AdTeamResponse> adTeamResponseList = adTeamResponses.toList();
        List<AdTeamFactoryCustom> listCustom = new ArrayList<>();
        adTeamResponseList.forEach(xx -> {
            AdTeamFactoryCustom adTeamFactoryCustom = new AdTeamFactoryCustom();
            adTeamFactoryCustom.setId(xx.getId());
            adTeamFactoryCustom.setName(xx.getName());
            adTeamFactoryCustom.setDescriptions(xx.getDescriptions());
            adTeamFactoryCustom.setStt(xx.getStt());
            adTeamFactoryCustom.setNumberMember(adTeamRepository.countNumberMemberOfTeam(xx.getId()));
            List<String> memberStr = adTeamRepository.getAllMemberOfTeam(xx.getId());
            List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(memberStr);
            adTeamFactoryCustom.setListMember(listResponse);
            listCustom.add(adTeamFactoryCustom);
        });
        PageableObject<AdTeamFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(adTeamResponses.getNumber());
        pageableObject.setTotalPages(adTeamResponses.getTotalPages());
        return pageableObject;
    }

    @Override
    public String deleteTeam(String id) {
        Optional<TeamFactory> findTeamById = adTeamRepository.findById(id);
        Integer countTeams = adTeamRepository.countMemberTeamByTeamId(id);
        if (!findTeamById.isPresent()) {
            throw new RestApiException(Message.TEAM_NOT_EXISTS);
        }
        if (countTeams != null && countTeams > 0) {
            throw new RestApiException(Message.TEAM_MEMBER_TEAM_ALREADY_EXISTS);
        }
        adTeamRepository.delete(findTeamById.get());
        return id;
    }

    @Override
    public TeamFactory detailTeam(String id) {
        return adTeamRepository.findById(id).get();
    }

    @Override
    public List<AdAllMemberFactoryResponse> detailMemberTeamFactory(String id) {
        List<AdTemplateMemberFactoryResponse> listStrIdMember = adTeamRepository.getMemberTeamFactory(id);
        List<String> idList = listStrIdMember.stream()
                .map(AdTemplateMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idList);
        List<AdAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listStrIdMember.forEach(idMe -> {
            listResponse.forEach(res -> {
                if(idMe.getMemberId().equals(res.getId())) {
                    AdAllMemberFactoryResponse adAllMemberFactoryResponse = new AdAllMemberFactoryResponse();
                    adAllMemberFactoryResponse.setId(idMe.getId());
                    adAllMemberFactoryResponse.setMemberId(idMe.getMemberId());
                    adAllMemberFactoryResponse.setUserName(res.getUserName());
                    adAllMemberFactoryResponse.setPicture(res.getPicture());
                    adAllMemberFactoryResponse.setIdMemberTeamFactory(idMe.getIdMemberTeamFactory());
                    adAllMemberFactoryResponse.setName(res.getName());
                    adAllMemberFactoryResponse.setEmail(res.getEmail());
                    listCustom.add(adAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }

    @Override
    public List<AdAllMemberFactoryResponse> getAllMemberFactory() {
        List<MemberFactory> listMemberFactory = adMemberFactoryRepository.getAllMemberFactory();
        List<String> idList = listMemberFactory.stream()
                .map(MemberFactory::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = callApiIdentity.handleCallApiGetListUserByListId(idList);
        List<AdAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listMemberFactory.forEach(memberFactory -> {
            listResponse.forEach(response -> {
                if (memberFactory.getMemberId().equals(response.getId())) {
                    AdAllMemberFactoryResponse adAllMemberFactoryResponse = new AdAllMemberFactoryResponse();
                    adAllMemberFactoryResponse.setMemberId(memberFactory.getMemberId());
                    adAllMemberFactoryResponse.setEmail(response.getEmail());
                    adAllMemberFactoryResponse.setPicture(response.getPicture());
                    adAllMemberFactoryResponse.setId(memberFactory.getId());
                    adAllMemberFactoryResponse.setUserName(response.getUserName());
                    adAllMemberFactoryResponse.setName(response.getName());
                    listCustom.add(adAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }

    @Override
    public List<String> addMembers(@Valid AdCreateMembersInTeamRequest request) {
        List<String> check = adTeamRepository.checkMemberTeamFactory(request.getListMemberId(), request.getIdTeam());
        if (check != null && check.size() > 0) {
            throw new RestApiException(Message.MEMBER_FACTORY_IS_ALREADY_IN_THE_GROUP);
        }
        List<MemberTeamFactory> memberTeamFactoryList = new ArrayList<>();
        request.getListMemberId().forEach(xx -> {
            MemberTeamFactory memberTeamFactory = new MemberTeamFactory();
            memberTeamFactory.setTeamFactoryId(request.getIdTeam());
            memberTeamFactory.setMemberFactoryId(xx);
            memberTeamFactoryList.add(memberTeamFactory);
        });
        memberTeamFactoryRepository.saveAll(memberTeamFactoryList);
        return request.getListMemberId();
    }

    @Override
    public String deleteMemberTeamFactory(String id) {
        Optional<MemberTeamFactory> memberTeamFactoryFind = memberTeamFactoryRepository.findById(id);
        if(!memberTeamFactoryFind.isPresent()) {
            throw new RestApiException(Message.MEMBER_TEAM_FACTORY_NOT_EXISTS);
        }
        memberTeamFactoryRepository.delete(memberTeamFactoryFind.get());
        return id;
    }

    @Override
    public List<String> deleteListMemberTeamFactory(@Valid AdDeleteListMemberTeamFactoryRequest request) {
        List<MemberTeamFactory> memberTeamFactoryList = memberTeamFactoryRepository.findAllById(request.getListIdMemberFactory());
        if(memberTeamFactoryList.isEmpty()) {
            throw new RestApiException(Message.MEMBER_TEAM_FACTORY_NOT_EXISTS);
        }
        memberTeamFactoryRepository.deleteAll(memberTeamFactoryList);
        return request.getListIdMemberFactory();
    }
}
