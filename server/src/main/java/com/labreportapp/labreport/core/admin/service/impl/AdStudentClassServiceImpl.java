package com.labreportapp.labreport.core.admin.service.impl;

import com.labreportapp.labreport.core.admin.model.response.AdStudentCallApiRespone;
import com.labreportapp.labreport.core.admin.model.response.AdStudentClassesRespone;
import com.labreportapp.labreport.core.admin.repository.AdStudentClassRepository;
import com.labreportapp.labreport.core.admin.service.AdStudentClassService;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdStudentClassServiceImpl implements AdStudentClassService {

    @Autowired
    private AdStudentClassRepository adStudentClassRepository;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass) {
        List<AdStudentClassesRespone> listRepository = adStudentClassRepository
                .findStudentClassByIdClass(idClass);

        List<String> idStudentList = listRepository.stream()
                .map(AdStudentClassesRespone::getIdStudent)
                .collect(Collectors.toList());

        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<AdStudentCallApiRespone> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    AdStudentCallApiRespone obj = new AdStudentCallApiRespone();
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
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }
}
