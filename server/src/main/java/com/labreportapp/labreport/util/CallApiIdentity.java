package com.labreportapp.labreport.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreportapp.labreport.core.common.response.RolesResponse;
import com.labreportapp.labreport.core.common.response.SimpleResponse;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.infrastructure.apiconstant.LabReportAppConstants;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author thangncph26123
 */
@Component
public class CallApiIdentity {

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${domain.identity}")
    private String domain;

    public List<SimpleResponse> handleCallApiGetListUserByListId(List<String> listIdUser) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_USER_BY_LIST_ID;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);
            System.out.println(authorizationToken);
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
            System.out.println(response.size() + " aaaaaaaaaaa");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SimpleResponse> handleCallApiGetUserByRoleAndModule(String roleCode) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_ALL_USER_BY_ROLE_AND_MODULE;

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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SimpleResponse handleCallApiGetUserById(String idUSer) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_USER_BY_ID;
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SimpleResponse handleCallApiGetUserByEmail(String email) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_USER_BY_EMAIL;
            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<SimpleResponse> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + email, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<SimpleResponse>() {
                            });

            SimpleResponse response = responseEntity.getBody();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<SimpleResponse> handleCallApiGetListUserByListEmail(List<String> listEmail) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_USER_BY_LIST_EMAIL;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonList = null;
            try {
                jsonList = objectMapper.writeValueAsString(listEmail);
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object handleCallApiGetRoleUserByIdUserAndModuleCode(String idUSer) {
        try {
            String apiUrl = domain + ApiConstants.API_GET_ROLES_USER_BY_ID_USER_AND_MODULE_CODE;
            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<List<RolesResponse>> responseEntity =
                    restTemplate.exchange(apiUrl + "/" + idUSer + "/" + LabReportAppConstants.MODULE_CODE, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<List<RolesResponse>>() {
                            });

            List<RolesResponse> response = responseEntity.getBody();
            List<String> roles = response.stream()
                    .map(RolesResponse::getRoleCode)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            System.out.println(roles.get(0));
            System.out.println(roles.get(1));
            System.out.println(roles.get(2));
            if (roles.size() > 1) {
                return roles;
            }
            return roles.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
