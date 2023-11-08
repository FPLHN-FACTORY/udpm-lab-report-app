package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindMemberFactoryRequest;
import com.labreportapp.labreport.core.teacher.model.response.TeMemberFactoryCustom;
import com.labreportapp.labreport.core.teacher.model.response.TeMemberFactoryResponse;
import com.labreportapp.labreport.core.teacher.repository.TeMemberFactoryRepository;
import com.labreportapp.labreport.core.teacher.service.TeMemberFactoryService;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
import com.labreportapp.labreport.util.CallApiIdentity;
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
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
@Validated
public class TeMemberFactoryServiceImpl implements TeMemberFactoryService {

    @Autowired
    private TeMemberFactoryRepository teMemberFactoryRepository;

    @Autowired
    private CallApiIdentity callApiIdentity;

    @Autowired
    @Qualifier(TeamFactoryRepository.NAME)
    private TeamFactoryRepository teamFactoryRepository;

    @Override
    public PageableObject<TeMemberFactoryCustom> getPage(TeFindMemberFactoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<TeMemberFactoryResponse> responsePage = teMemberFactoryRepository.getAllRoleProject(pageable, request);
        List<String> idList = responsePage.getContent().stream()
                .map(TeMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = new ArrayList<>();
        if (idList != null && idList.size() > 0) {
            listResponse = callApiIdentity.handleCallApiGetListUserByListId(idList);
        }
        List<SimpleResponse> finalListResponse = listResponse;
        List<TeMemberFactoryCustom> listCustom = new ArrayList<>();
        responsePage.getContent().forEach(page -> {
            finalListResponse.forEach(response -> {
                if (page.getMemberId().equals(response.getId())) {
                    TeMemberFactoryCustom teMemberFactoryCustom = new TeMemberFactoryCustom();
                    teMemberFactoryCustom.setId(page.getId());
                    teMemberFactoryCustom.setMemberId(page.getMemberId());
                    teMemberFactoryCustom.setRoleMemberFactory(page.getRoleMemberFactory());
                    teMemberFactoryCustom.setNumberTeam(page.getNumberTeam());
                    teMemberFactoryCustom.setPicture(response.getPicture());
                    teMemberFactoryCustom.setStt(page.getStt());
                    teMemberFactoryCustom.setEmail(page.getEmail());
                    teMemberFactoryCustom.setStatusMemberFactory(page.getStatusMemberFactory());
                    teMemberFactoryCustom.setName(response.getName());
                    teMemberFactoryCustom.setUserName(response.getUserName());
                    listCustom.add(teMemberFactoryCustom);
                }
            });
        });
        PageableObject<TeMemberFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(responsePage.getNumber());
        pageableObject.setTotalPages(responsePage.getTotalPages());
        return pageableObject;
    }

    @Override
    public List<SimpleEntityProjection> getRoles() {
        return teMemberFactoryRepository.getRoles();
    }

    @Override
    public List<SimpleEntityProjection> getTeams() {
        return teamFactoryRepository.getTeams();
    }

    @Override
    public Integer getNumberMemberFactory() {
        return teMemberFactoryRepository.getNumberMemberFactory();
    }

}
