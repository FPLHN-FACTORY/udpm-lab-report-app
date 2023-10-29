package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindTeamFactoryRequest;
import com.labreportapp.labreport.core.student.model.response.StAllMemberFactoryResponse;
import com.labreportapp.labreport.core.student.model.response.StTeamFactoryCustom;
import com.labreportapp.labreport.core.student.model.response.StTeamFactoryResponse;
import com.labreportapp.labreport.core.student.model.response.StTemplateMemberFactoryResponse;
import com.labreportapp.labreport.core.student.repository.StMemberFactoryRepository;
import com.labreportapp.labreport.core.student.repository.StTeamFactoryRepository;
import com.labreportapp.labreport.core.student.service.StTeamFactoryService;
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
public class StTeamFactoryServiceImpl implements StTeamFactoryService {

    @Autowired
    private StTeamFactoryRepository stTeamFactoryRepository;

    private FormUtils formUtils = new FormUtils();

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    private StMemberFactoryRepository stMemberFactoryRepository;

    @Override
    public List<TeamFactory> findAllTeam(Pageable pageable) {
        return stTeamFactoryRepository.getAllTeam(pageable);
    }

    @Override
    public PageableObject<StTeamFactoryCustom> searchTeam(StFindTeamFactoryRequest rep) {
        Pageable pageable = PageRequest.of(rep.getPage() - 1, rep.getSize());
        Page<StTeamFactoryResponse> stTeamResponses = stTeamFactoryRepository.searchTeam(rep, pageable);
        List<StTeamFactoryResponse> stTeamResponseList = stTeamResponses.toList();
        List<StTeamFactoryCustom> listCustom = new ArrayList<>();
        stTeamResponseList.forEach(xx -> {
            StTeamFactoryCustom stTeamFactoryCustom = new StTeamFactoryCustom();
            stTeamFactoryCustom.setId(xx.getId());
            stTeamFactoryCustom.setName(xx.getName());
            stTeamFactoryCustom.setDescriptions(xx.getDescriptions());
            stTeamFactoryCustom.setStt(xx.getStt());
            stTeamFactoryCustom.setNumberMember(stTeamFactoryRepository.countNumberMemberOfTeam(xx.getId()));
            List<String> memberStr = stTeamFactoryRepository.getAllMemberOfTeam(xx.getId());
            List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(memberStr);
            stTeamFactoryCustom.setListMember(listResponse);
            listCustom.add(stTeamFactoryCustom);
        });
        PageableObject<StTeamFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(stTeamResponses.getNumber());
        pageableObject.setTotalPages(stTeamResponses.getTotalPages());
        return pageableObject;
    }

    @Override
    public TeamFactory detailTeam(String id) {
        return stTeamFactoryRepository.findById(id).get();
    }

    @Override
    public List<StAllMemberFactoryResponse> detailMemberTeamFactory(String id) {
        List<StTemplateMemberFactoryResponse> listStrIdMember = stTeamFactoryRepository.getMemberTeamFactory(id);
        List<String> idList = listStrIdMember.stream()
                .map(StTemplateMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        List<StAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listStrIdMember.forEach(idMe -> {
            listResponse.forEach(res -> {
                if(idMe.getMemberId().equals(res.getId())) {
                    StAllMemberFactoryResponse stAllMemberFactoryResponse = new StAllMemberFactoryResponse();
                    stAllMemberFactoryResponse.setId(idMe.getId());
                    stAllMemberFactoryResponse.setMemberId(idMe.getMemberId());
                    stAllMemberFactoryResponse.setUserName(res.getUserName());
                    stAllMemberFactoryResponse.setPicture(res.getPicture());
                    stAllMemberFactoryResponse.setIdMemberTeamFactory(idMe.getIdMemberTeamFactory());
                    stAllMemberFactoryResponse.setName(res.getName());
                    stAllMemberFactoryResponse.setEmail(res.getEmail());
                    listCustom.add(stAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }

    @Override
    public List<StAllMemberFactoryResponse> getAllMemberFactory() {
        List<MemberFactory> listMemberFactory = stMemberFactoryRepository.getAllMemberFactory();
        List<String> idList = listMemberFactory.stream()
                .map(MemberFactory::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        List<StAllMemberFactoryResponse> listCustom = new ArrayList<>();
        listMemberFactory.forEach(memberFactory -> {
            listResponse.forEach(response -> {
                if (memberFactory.getMemberId().equals(response.getId())) {
                    StAllMemberFactoryResponse stAllMemberFactoryResponse = new StAllMemberFactoryResponse();
                    stAllMemberFactoryResponse.setMemberId(memberFactory.getMemberId());
                    stAllMemberFactoryResponse.setEmail(response.getEmail());
                    stAllMemberFactoryResponse.setPicture(response.getPicture());
                    stAllMemberFactoryResponse.setId(memberFactory.getId());
                    stAllMemberFactoryResponse.setUserName(response.getUserName());
                    stAllMemberFactoryResponse.setName(response.getName());
                    listCustom.add(stAllMemberFactoryResponse);
                }
            });
        });
        return listCustom;
    }
}
