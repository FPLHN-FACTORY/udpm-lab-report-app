package com.labreportapp.core.teacher.service.impl;

import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.core.teacher.model.response.TePointImportRespone;
import com.labreportapp.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.core.teacher.service.TeStudentClassesService;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hieundph25894
 */
@Service
public class TeStudentClassesServiceImpl implements TeStudentClassesService {

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<TeStudentCallApiResponse> searchStudentClassesByIdClass(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesRespone> listRepository = teStudentClassesRepository
                .findStudentClassByIdClass(teFindStudentClasses);
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?id=");
        int sizeRepo = listRepository.size();
        for (int i = 0; i < sizeRepo; i++) {
            TeStudentClassesRespone item = listRepository.get(i);
            if (item.getIdStudent() != null) {
                if (i > 0) {
                    queryParams.append("|");
                }
                queryParams.append(item.getIdStudent());
            }
        }
        String result = queryParams.toString();
        String apiUrl = ApiConstants.API_LIST_STUDENT;
        ResponseEntity<List<TeStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl + result, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeStudentCallApiResponse>>() {
                        });
        List<TeStudentCallApiResponse> listRespone = responseEntity.getBody();
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
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
                    obj.setSubjectName(reposi.getSubjectName());
                    listReturn.add(obj);
                }
            });
        });
        return listReturn;
    }

    @Override
    public List<SimpleResponse> searchAllStudentByIdClass(String idClass) {
        List<TePointImportRespone> listRepository = teStudentClassesRepository
                .findAllStudentClassForPointByIdClass(idClass);
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?id=");
        int sizeRepo = listRepository.size();
        for (int i = 0; i < sizeRepo; i++) {
            TePointImportRespone item = listRepository.get(i);
            if (item.getIdStudent() != null) {
                if (i > 0) {
                    queryParams.append("|");
                }
                queryParams.append(item.getIdStudent());     
            }
        }
        String result = queryParams.toString();
        String apiUrl = ApiConstants.API_LIST_STUDENT;
        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl + result, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });
        List<SimpleResponse> listRespone = responseEntity.getBody();
        return listRespone;
    }

    @Override
    public List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesRespone> listRepository = teStudentClassesRepository
                .findStudentClassByIdClassAndIdTeam(teFindStudentClasses);
        StringBuilder queryParams = new StringBuilder();
        queryParams.append("?id=");
        int sizeRepo = listRepository.size();
        for (int i = 0; i < sizeRepo; i++) {
            TeStudentClassesRespone item = listRepository.get(i);
            if (item.getIdStudent() != null) {
                if (i > 0) {
                    queryParams.append("|");
                }
                queryParams.append(item.getIdStudent());
            }
        }
        String result = queryParams.toString();
        String apiUrl = ApiConstants.API_LIST_STUDENT;
        ResponseEntity<List<TeStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl + result, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeStudentCallApiResponse>>() {
                        });
        List<TeStudentCallApiResponse> listRespone = responseEntity.getBody();
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
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

    @Override
    public List<TeStudentCallApiResponse> callApiStudent(TeFindStudentApiRequest teFindStudentApiRequest) {
        String apiUrl = ApiConstants.API_LIST_STUDENT;

        ResponseEntity<List<TeStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?id=" + teFindStudentApiRequest.getListId(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeStudentCallApiResponse>>() {
                        });
        List<TeStudentCallApiResponse> response = responseEntity.getBody();
        return response;
    }

}
