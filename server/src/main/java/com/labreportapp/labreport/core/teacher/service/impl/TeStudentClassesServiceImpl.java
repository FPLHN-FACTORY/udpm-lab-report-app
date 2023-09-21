package com.labreportapp.labreport.core.teacher.service.impl;

import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentApiRequest;
import com.labreportapp.labreport.core.teacher.model.request.TeFindStudentClasses;
import com.labreportapp.labreport.core.teacher.model.response.TePointImportRespone;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentCallApiResponse;
import com.labreportapp.labreport.core.teacher.model.response.TeStudentClassesRespone;
import com.labreportapp.labreport.core.teacher.repository.TeStudentClassesRepository;
import com.labreportapp.labreport.core.teacher.service.TeStudentClassesService;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.util.ConvertRequestCallApiIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hieundph25894
 */
@Service
public class TeStudentClassesServiceImpl implements TeStudentClassesService {

    @Autowired
    private TeStudentClassesRepository teStudentClassesRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConvertRequestCallApiIdentity convertRequestCallApiIdentity;

    @Override
    public List<TeStudentCallApiResponse> searchApiStudentClassesByIdClass(String idClass) {
        List<TeStudentClassesRespone> listRepository = teStudentClassesRepository
                .findStudentClassByIdClass(idClass);
        List<String> idStudentList = listRepository.stream()
                .map(TeStudentClassesRespone::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
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

    @Override
    public List<SimpleResponse> searchAllStudentByIdClass(String idClass) {
        List<TePointImportRespone> listRepository = teStudentClassesRepository
                .findAllStudentClassForPointByIdClass(idClass);
        List<String> idStudentList = listRepository.stream()
                .map(TePointImportRespone::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        return listRespone;
    }

    @Override
    public List<TeStudentCallApiResponse> searchStudentClassesByIdClassAndIdTeam(TeFindStudentClasses teFindStudentClasses) {
        List<TeStudentClassesRespone> listRepository = teStudentClassesRepository
                .findStudentClassByIdClassAndIdTeam(teFindStudentClasses);
        List<String> idStudentList = listRepository.stream()
                .map(TeStudentClassesRespone::getIdStudent)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> listRespone = convertRequestCallApiIdentity.handleCallApiGetListUserByListId(idStudentList);
        List<TeStudentCallApiResponse> listReturn = new ArrayList<>();
        listRepository.forEach(reposi -> {
            listRespone.forEach(respone -> {
                if (reposi.getIdStudent().equals(respone.getId())) {
                    TeStudentCallApiResponse obj = new TeStudentCallApiResponse();
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

    @Override
    public List<TeStudentCallApiResponse> callApiStudent(TeFindStudentApiRequest teFindStudentApiRequest) {
        String apiUrl = ApiConstants.API_GET_USER_BY_LIST_ID;

        ResponseEntity<List<TeStudentCallApiResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?id=" + teFindStudentApiRequest.getListId(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeStudentCallApiResponse>>() {
                        });
        List<TeStudentCallApiResponse> response = responseEntity.getBody();
        return response;
    }

}
