package com.labreportapp.labreport.core.admin.service.impl;


import com.labreportapp.labreport.core.admin.model.response.AdFeedBackResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiResponse;
import com.labreportapp.labreport.core.admin.model.response.AdStudentClassesResponse;
import com.labreportapp.labreport.core.admin.repository.AdFeedBackRepository;
import com.labreportapp.labreport.core.admin.service.AdFeedBackService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author quynhncph26201
 */
@Service
public class AdFeedBackServiceImpl implements AdFeedBackService {
    @Autowired
    private AdFeedBackRepository repository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdFeedBackResponse> searchFeedBack(String idClass) {
        List<AdFeedBackResponse> list = repository.getAllFeedBack(idClass);
        return list;
    }

    @Override
    public List<AdStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass) {
        List<AdStudentClassesResponse> listRepository = repository
                .findStudentClassByIdClass(idClass);
        List<String> idStudentList = listRepository.stream()
                .map(AdStudentClassesResponse::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<AdStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    AdStudentCallApiResponse obj = new AdStudentCallApiResponse();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUsername(respone.getUserName());
                    obj.setEmail(reposi.getEmailStudentClass());
                    obj.setIdStudent(respone.getId());
                    obj.setIdStudentClass(reposi.getIdStudentClass());
                    obj.setRole(reposi.getRole());
                    obj.setStatusStudent(reposi.getStatusStudent());
                    obj.setIdTeam(reposi.getIdTeam());
                    obj.setCodeTeam(reposi.getCodeTeam());
                    obj.setNameTeam(reposi.getNameTeam());
                    obj.setSubjectName(reposi.getSubjectName());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }
}
