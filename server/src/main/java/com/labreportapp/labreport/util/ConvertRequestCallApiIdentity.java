package com.labreportapp.labreport.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.infrastructure.apiconstant.LabReportAppConstants;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author thangncph26123
 */
@Component
public class ConvertRequestCallApiIdentity {

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private RestTemplate restTemplate;

    public List<SimpleResponse> handleCallApiGetListUserByListId(List<String> listIdUser) {
        String apiUrl = ApiConstants.API_GET_USER_BY_LIST_ID;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String authorizationToken = "Bearer " + labReportAppSession.getToken();
        headers.set("Authorization", authorizationToken);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonList = null;
        try {
            jsonList = objectMapper.writeValueAsString(listIdUser);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(jsonList, headers);

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });

        List<SimpleResponse> response = responseEntity.getBody();
        return response;
    }

    public List<SimpleResponse> handleCallApiGetUserByRoleAndModule(String roleCode) {
        String apiUrl = ApiConstants.API_GET_ALL_USER_BY_ROLE_AND_MODULE;

        HttpHeaders headers = new HttpHeaders();
        String authorizationToken = "Bearer " + labReportAppSession.getToken();
        headers.set("Authorization", authorizationToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<List<SimpleResponse>> responseEntity =
                restTemplate.exchange(apiUrl + "?roleCode=" + roleCode + "&moduleCode=" + LabReportAppConstants.MODULE_CODE, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<List<SimpleResponse>>() {
                        });

        List<SimpleResponse> response = responseEntity.getBody();
        return response;
    }

    public SimpleResponse handleCallApiGetUserById(String idUSer) {
        String apiUrl = ApiConstants.API_GET_USER_BY_ID;
        HttpHeaders headers = new HttpHeaders();
        String authorizationToken = "Bearer " + labReportAppSession.getToken();
        headers.set("Authorization", authorizationToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<SimpleResponse> responseEntity =
                restTemplate.exchange(apiUrl + "/" + idUSer, HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<SimpleResponse>() {
                        });

        SimpleResponse response = responseEntity.getBody();
        return response;
    }

}
