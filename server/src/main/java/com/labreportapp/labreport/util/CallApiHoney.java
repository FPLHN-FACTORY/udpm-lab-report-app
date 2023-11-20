package com.labreportapp.labreport.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labreportapp.labreport.core.common.base.CategoryHoneyResponse;
import com.labreportapp.labreport.core.common.base.HoneyConversionRequest;
import com.labreportapp.labreport.core.common.base.ResponseObject;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
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

/**
 * @author todo thangncph26123
 */
@Component
public class CallApiHoney {

    @Autowired
    private LabReportAppSession labReportAppSession;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${domain.honey}")
    private String domainHoney;

    public List<CategoryHoneyResponse> getAllListCategory() {
        try {
            String apiUrl = domainHoney + ApiConstants.API_GET_ALL_CATEGORY;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);

            ResponseEntity<List<CategoryHoneyResponse>> responseEntity =
                    restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                            new ParameterizedTypeReference<List<CategoryHoneyResponse>>() {
                            });

            List<CategoryHoneyResponse> response = responseEntity.getBody();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean addPointStudentLabReportApp(HoneyConversionRequest request) {
        try {
            String apiUrl = domainHoney + ApiConstants.API_ADD_POINT_LAB_REPORT_APP;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String authorizationToken = "Bearer " + labReportAppSession.getToken();
            headers.set("Authorization", authorizationToken);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonList = null;
            try {
                jsonList = objectMapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

            HttpEntity<String> httpEntity = new HttpEntity<>(jsonList, headers);

            ResponseEntity<Boolean> responseEntity =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity,
                            new ParameterizedTypeReference<Boolean>() {
                            });

            Boolean response = responseEntity.getBody();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
