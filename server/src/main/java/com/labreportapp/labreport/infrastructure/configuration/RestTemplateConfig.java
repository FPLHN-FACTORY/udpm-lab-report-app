package com.labreportapp.labreport.infrastructure.configuration;

//import org.apache.hc.client5.http.classic.HttpClient;
//import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
//import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
//import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
//import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
//import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
//import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
//import org.apache.hc.core5.http.URIScheme;
//import org.apache.hc.core5.http.config.Registry;
//import org.apache.hc.core5.http.config.RegistryBuilder;
//import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate()// throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException
    {

//        SSLContext sslContext = SSLContexts.custom()
//                .loadTrustMaterial(new TrustSelfSignedStrategy())
//                .build();
//        ;
//        Registry<ConnectionSocketFactory> socketRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                .register(URIScheme.HTTPS.getId(), new SSLConnectionSocketFactory(sslContext))
//                .register(URIScheme.HTTP.getId(), new PlainConnectionSocketFactory())
//                .build();
//
//        HttpClient httpClient = HttpClientBuilder.create()
//                .setConnectionManager(new PoolingHttpClientConnectionManager(socketRegistry))
//                .setConnectionManagerShared(true)
//                .build();

//        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//       return new RestTemplate(requestFactory);
        return new RestTemplate();
    }
}
