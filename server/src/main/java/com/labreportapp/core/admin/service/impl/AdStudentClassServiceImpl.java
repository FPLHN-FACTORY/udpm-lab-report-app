package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.model.response.AdStudentCallApiRespone;
import com.labreportapp.core.admin.model.response.AdStudentClassesRespone;
import com.labreportapp.core.admin.repository.AdStudentClassRepository;
import com.labreportapp.core.admin.service.AdStudentClassService;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdStudentClassServiceImpl implements AdStudentClassService {
    @Autowired
    private AdStudentClassRepository adStudentClassRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<AdStudentCallApiRespone> findStudentClassByIdClass(String idClass) {
//        return adStudentClassRepository.findStudentClassByIdClass(idClass);
        List<AdStudentClassesRespone> listRepository = adStudentClassRepository
                .findStudentClassByIdClass(idClass);
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?id=");
        int sizeRepo = listRepository.size();
        for (int i = 0; i < sizeRepo; i++) {
            AdStudentClassesRespone item = listRepository.get(i);
            if (item.getIdStudent() != null) {
                if (i > 0) {
                    queryParams.append("|");
                }
                queryParams.append(item.getIdStudent());
            }
        }
        String result = queryParams.toString();
        String apiUrl = ApiConstants.API_LIST_STUDENT;
        ResponseEntity<List<AdStudentCallApiRespone>> responseEntity =
                restTemplate.exchange(apiUrl + result, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<AdStudentCallApiRespone>>() {
                        });
        List<AdStudentCallApiRespone> listRespone = responseEntity.getBody();
        List<AdStudentCallApiRespone> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    AdStudentCallApiRespone obj = new AdStudentCallApiRespone();
                    obj.setId(respone.getId());
                    obj.setName(respone.getName());
                    obj.setUsername(respone.getUsername());
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
