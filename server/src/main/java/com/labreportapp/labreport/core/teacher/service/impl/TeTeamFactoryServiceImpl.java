package com.labreportapp.labreport.core.teacher.service.impl;


import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindTeamFactoryRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeAllMemberFactoryResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamFactoryCustom;
import com.labreportapp.labreport.core.teacher.model.response.TeTeamFactoryResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeTemplateMemberFactoryResponse;
import com.labreportapp.labreport.core.teacher.repository.TeMemberFactoryRepository;
import com.labreportapp.labreport.core.teacher.repository.TeTeamFactoryRepository;
import com.labreportapp.labreport.core.teacher.service.TeTeamFactoryService;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.entity.TeamFactory;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.labreport.util.FormUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class TeTeamFactoryServiceImpl implements TeTeamFactoryService {

    @Autowired
    private TeTeamFactoryRepository teTeamFactoryRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private TeMemberFactoryRepository teMemberFactoryRepository;

    @Override
    public List<TeamFactory> findAllTeam(Pageable pageable) {
        return teTeamFactoryRepository.getAllTeam(pageable);
    }

    @Override
    public PageableObject<TeTeamFactoryCustom> searchTeam(TeFindTeamFactoryRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<TeTeamFactoryResponse> teTeamResponses = teTeamFactoryRepository.searchTeam(rep, pageable);
        List<TeTeamFactoryResponse> teTeamResponseList = teTeamResponses.toList();
        List<TeTeamFactoryCustom> listCustom = new ArrayList<>();
        teTeamResponseList.forEach(xx -> {
            TeTeamFactoryCustom teTeamFactoryCustom = new TeTeamFactoryCustom();
            teTeamFactoryCustom.setId(xx.getId());
            teTeamFactoryCustom.setName(xx.getName());
            teTeamFactoryCustom.setDescriptions(xx.getDescriptions());
            teTeamFactoryCustom.setStt(xx.getStt());
            teTeamFactoryCustom.setNumberMember(teTeamFactoryRepository.countNumberMemberOfTeam(xx.getId()));
            List<String> memberStr = teTeamFactoryRepository.getAllMemberOfTeam(xx.getId());
            List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(memberStr);
            teTeamFactoryCustom.setListMember(listResponse);
            listCustom.add(teTeamFactoryCustom);
        });
        PageableObject<TeTeamFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(teTeamResponses.getNumber());
        pageableObject.setTotalPages(teTeamResponses.getTotalPages());
        return pageableObject;
    }

    @Override
    public TeamFactory detailTeam(String id) {
        return teTeamFactoryRepository.findById(id).get();
    }

    @Override
    public List<TeAllMemberFactoryResponse> detailMemberTeamFactory(String id) {
        List<TeTemplateMemberFactoryResponse> listStrIdMember = teTeamFactoryRepository.getMemberTeamFactory(id);
        List<String> idList = listStrIdMember.stream()
                .map(TeTemplateMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        List<TeAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listStrIdMember.forEach(idMe -> {
            listResponse.forEach(res -> {
                if (idMe.getMemberId().equals(res.getId())) {
                    TeAllMemberFactoryResponse teAllMemberFactoryResponse = new TeAllMemberFactoryResponse();
                    teAllMemberFactoryResponse.setId(idMe.getId());
                    teAllMemberFactoryResponse.setMemberId(idMe.getMemberId());
                    teAllMemberFactoryResponse.setUserName(res.getUserName());
                    teAllMemberFactoryResponse.setPicture(res.getPicture());
                    teAllMemberFactoryResponse.setIdMemberTeamFactory(idMe.getIdMemberTeamFactory());
                    teAllMemberFactoryResponse.setName(res.getName());
                    teAllMemberFactoryResponse.setEmail(res.getEmail());
                    listCustom.add(teAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }

    @Override
    public List<TeAllMemberFactoryResponse> getAllMemberFactory() {
        List<MemberFactory> listMemberFactory = teMemberFactoryRepository.getAllMemberFactory();
        List<String> idList = listMemberFactory.stream()
                .map(MemberFactory::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        List<TeAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listMemberFactory.forEach(memberFactory -> {
            listResponse.forEach(response -> {
                if (memberFactory.getMemberId().equals(response.getId())) {
                    TeAllMemberFactoryResponse teAllMemberFactoryResponse = new TeAllMemberFactoryResponse();
                    teAllMemberFactoryResponse.setMemberId(memberFactory.getMemberId());
                    teAllMemberFactoryResponse.setEmail(response.getEmail());
                    teAllMemberFactoryResponse.setPicture(response.getPicture());
                    teAllMemberFactoryResponse.setId(memberFactory.getId());
                    teAllMemberFactoryResponse.setUserName(response.getUserName());
                    teAllMemberFactoryResponse.setName(response.getName());
                    listCustom.add(teAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }

}
