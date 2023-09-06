package com.labreportapp.core.admin.service.impl;

import com.labreportapp.core.admin.service.AdTeacherService;
import com.labreportapp.core.common.response.SimpleResponse;
import com.labreportapp.infrastructure.apiconstant.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author thangncph26123
 */
@Service
public class AdTeacherServiceImpl implements AdTeacherService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<SimpleResponse> getAllTeacher() {
        String apiUrl = ApiConstants.API_LIST_TEACHER;

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });

        List<SimpleResponse> response = responseEntity.getBody();
        return response;
    }
}
