package com.labreportapp.portalprojects.core.admin.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.entity.MemberFactory;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import com.labreportapp.portalprojects.core.admin.model.response.AdPotalsMemberFactoryCustom;
import com.labreportapp.portalprojects.core.admin.repository.AdPotalsMemberFactoryRepository;
import com.labreportapp.portalprojects.core.admin.service.AdPotalsMemberFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class AdPotalsMemberFactoryServiceImpl implements AdPotalsMemberFactoryService {

    @Autowired
    private AdPotalsMemberFactoryRepository adPotalsMemberFactoryRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdPotalsMemberFactoryCustom> getAllMemberFactory() {
        List<MemberFactory> listMemberFactory = adPotalsMemberFactoryRepository.getAllMemberFactory();
        if (listMemberFactory.size() == 0) {
            return null;
        }
        List<String> idList = listMemberFactory.stream()
                .map(MemberFactory::getMemberId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listResponse = new ArrayList<>();
        if (idList != null && idList.size() > 0) {
            listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
        }
        List<SimpleResponse> finalListResponse = listResponse;
        List<AdPotalsMemberFactoryCustom> listReturn = new ArrayList<>();
        listMemberFactory.forEach(db -> {
            finalListResponse.forEach(call -> {
                if (db.getMemberId().equals(call.getId())) {
                    AdPotalsMemberFactoryCustom obj = new AdPotalsMemberFactoryCustom();
                    obj.setId(db.getId());
                    obj.setMemberId(db.getMemberId());
                    obj.setEmail(db.getEmail());
                    obj.setName(call.getName());
                    obj.setUserName(call.getUserName());
                    obj.setPicture(call.getPicture());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }
    //    @Override
//    public PageableObject<AdMemberFactoryCustom> getPage(final AdFindMemberFactoryRequest request) {
//        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
//        Page<AdMemberFactoryResponse> responsePage = adMemberFactoryRepository.getAllRoleProject(pageable, request);
//        List<String> idList = responsePage.getContent().stream()
//                .map(AdMemberFactoryResponse::getMemberId)
//                .filter(Objects::nonNull)
//                .distinct()
//                .collect(Collectors.toList());
//        List<SimpleResponse> listResponse = new ArrayList<>();
//        if (idList != null && idList.size() > 0) {
//            listResponse = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idList);
//        }
//        List<SimpleResponse> finalListResponse = listResponse;
//        List<AdMemberFactoryCustom> listCustom = new ArrayList<>();
//        responsePage.getContent().forEach(page -> {
//            finalListResponse.forEach(response -> {
//                if (page.getMemberId().equals(response.getId())) {
//                    AdMemberFactoryCustom adMemberFactoryCustom = new AdMemberFactoryCustom();
//                    adMemberFactoryCustom.setId(page.getId());
//                    adMemberFactoryCustom.setMemberId(page.getMemberId());
//                    adMemberFactoryCustom.setRoleMemberFactory(page.getRoleMemberFactory());
//                    adMemberFactoryCustom.setNumberTeam(page.getNumberTeam());
//                    adMemberFactoryCustom.setPicture(response.getPicture());
//                    adMemberFactoryCustom.setStt(page.getStt());
//                    adMemberFactoryCustom.setEmail(page.getEmail());
//                    adMemberFactoryCustom.setStatusMemberFactory(page.getStatusMemberFactory());
//                    adMemberFactoryCustom.setName(response.getName());
//                    adMemberFactoryCustom.setUserName(response.getUserName());
//                    listCustom.add(adMemberFactoryCustom);
//                }
//            });
//        });
//        PageableObject<AdMemberFactoryCustom> pageableObject = new PageableObject<>();
//        pageableObject.setData(listCustom);
//        pageableObject.setCurrentPage(responsePage.getNumber());
//        pageableObject.setTotalPages(responsePage.getTotalPages());
//        return pageableObject;
//    }
}
