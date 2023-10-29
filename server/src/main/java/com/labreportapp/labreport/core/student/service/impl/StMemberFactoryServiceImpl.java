package com.labreportapp.labreport.core.student.service.impl;

import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.core.common.base.SimpleEntityProjection;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.student.model.request.StFindMemberFactoryRequest;
import com.labreportapp.labreport.core.student.model.response.StMemberFactoryCustom;
import com.labreportapp.labreport.core.student.model.response.StMemberFactoryResponse;
import com.labreportapp.labreport.core.student.repository.StMemberFactoryRepository;
import com.labreportapp.labreport.core.student.service.StMemberFactoryService;
import com.labreportapp.labreport.repository.TeamFactoryRepository;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
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
public class StMemberFactoryServiceImpl implements StMemberFactoryService {

    @Autowired
    private StMemberFactoryRepository stMemberFactoryRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Autowired
    @Qualifier(TeamFactoryRepository.NAME)
    private TeamFactoryRepository teamFactoryRepository;

    @Override
    public PageableObject<StMemberFactoryCustom> getPage(StFindMemberFactoryRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<StMemberFactoryResponse> responsePage = stMemberFactoryRepository.getAllRoleProject(pageable, request);
        List<String> idList = responsePage.getContent().stream()
                .map(StMemberFactoryResponse::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = new ArrayList<>();
        if (idList != null && idList.size() > 0) {
            listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        }
        List<SimpleResponse> finalListResponse = listResponse;
        List<StMemberFactoryCustom> listCustom = new ArrayList<>();
        responsePage.getContent().forEach(page -> {
            finalListResponse.forEach(response -> {
                if (page.getMemberId().equals(response.getId())) {
                    StMemberFactoryCustom stMemberFactoryCustom = new StMemberFactoryCustom();
                    stMemberFactoryCustom.setId(page.getId());
                    stMemberFactoryCustom.setMemberId(page.getMemberId());
                    stMemberFactoryCustom.setRoleMemberFactory(page.getRoleMemberFactory());
                    stMemberFactoryCustom.setNumberTeam(page.getNumberTeam());
                    stMemberFactoryCustom.setPicture(response.getPicture());
                    stMemberFactoryCustom.setStt(page.getStt());
                    stMemberFactoryCustom.setEmail(page.getEmail());
                    stMemberFactoryCustom.setStatusMemberFactory(page.getStatusMemberFactory());
                    stMemberFactoryCustom.setName(response.getName());
                    stMemberFactoryCustom.setUserName(response.getUserName());
                    listCustom.add(stMemberFactoryCustom);
                }
            });
        });
        PageableObject<StMemberFactoryCustom> pageableObject = new PageableObject<>();
        pageableObject.setData(listCustom);
        pageableObject.setCurrentPage(responsePage.getNumber());
        pageableObject.setTotalPages(responsePage.getTotalPages());
        return pageableObject;
    }

    @Override
    public List<SimpleEntityProjection> getRoles() {
        return stMemberFactoryRepository.getRoles();
    }

    @Override
    public List<SimpleEntityProjection> getTeams() {
        return teamFactoryRepository.getTeams();
    }

    @Override
    public Integer getNumberMemberFactory() {
        return stMemberFactoryRepository.getNumberMemberFactory();
    }
}
