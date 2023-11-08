package com.labreportapp.labreport.util;

import com.google.gson.Gson;
import com.labreportapp.labreport.core.common.base.PageableObject;
import com.labreportapp.labreport.infrastructure.apiconstant.ApiConstants;
import com.labreportapp.labreport.infrastructure.logger.LoggerObject;
import com.labreportapp.labreport.infrastructure.session.LabReportAppSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author todo thangncph26123
 */
@Component
public class CallApiConsumer {

    @Autowired
    private LabReportAppSession session;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${domain.comsumer}")
    private String domainConsumer;

    public PageableObject handleCallApiReadFileLog(LoggerObject loggerObject, Integer page, Integer size) {
        try {
            String apiUrl = domainConsumer + ApiConstants.API_READ_FILE_LOG;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String authorizationToken = "Bearer " + session.getToken();
            headers.set("Authorization", authorizationToken);
            // Đối tượng HttpEntity chứa requestBody và headers
            HttpEntity<LoggerObject> requestEntity = new HttpEntity<>(loggerObject, headers);

            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(apiUrl + "?page=" + page + "&size=" + size, HttpMethod.POST, requestEntity,
                            new ParameterizedTypeReference<String>() {
                            });
            System.out.println(responseEntity.getBody());
            return new Gson().fromJson(responseEntity.getBody(), PageableObject.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<Resource> handleCallApiDowloadFileLog(String pathFile) {
        try {
            String apiUrl = domainConsumer + ApiConstants.API_DOWLOAD_FILE_LOG;
            HttpHeaders headers = new HttpHeaders();
            String authorizationToken = "Bearer " + session.getToken();
            headers.set("Authorization", authorizationToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

            ResponseEntity<Resource> responseEntity =
                    restTemplate.exchange(apiUrl + "?pathFile=" + pathFile, HttpMethod.GET, httpEntity,
                            new ParameterizedTypeReference<Resource>() {
                            });

            return responseEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}