package com.labreportapp.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public ClientHttpRequestInterceptor tokenInterceptor() {
//        return (request, body, execution) -> {
//            // Lấy token từ nơi bạn đã lưu trữ token (chẳng hạn trong một biến hoặc cache)
//            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIyNDM1YzdkNS05YmVjLTQ1YWMtOWJmZS0wOGRiYTg3NTIzZmUiLCJuYW1lIjoiTmd1eeG7hW4gQ8O0bmcgVGjhuq9uZyBQIEggMiA2IDEgMiAzIiwiZW1haWwiOiJ0aGFuZ25jcGgyNjEyM0BmcHQuZWR1LnZuIiwidXNlcm5hbWUiOiJ0aGFuZ25jcGgyNjEyMyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NMdEFxX2pGQm5ieUZUdUlCdllPbkdnS1ktTVQ5MEFvNkt0ZER6ZTY4Q1JQWW89czk2LWMiLCJpZHRyYWluaW5nZmFjaWxpdHkiOiI3OTZhNGZhNC04YWFiLTQyYzQtOWYzNS04NzBiYjAwMDVhZjEiLCJyb2xlIjpbIk1FTUJFUiIsIkFETUlOIl0sInJvbGVuYW1lcyI6WyJUaMOgbmggdmnDqm4iLCJRdeG6o24gdHLhu4sgdmnDqm4iXSwibmJmIjoxNjk0NzU0MTc0LCJleHAiOjE2OTQ3NTU5NzQsImlhdCI6MTY5NDc1NDE3NH0.NV-aqg24vOc1wwen-eo-Q4sBa4-oO1hCMoktzrGeSdc";
//
//            // Thêm token vào tiêu đề 'Authorization'
//            HttpHeaders headers = request.getHeaders();
//            headers.add("Authorization", "Bearer " + token);
//
//            return execution.execute(request, body);
//        };
//    }
}
