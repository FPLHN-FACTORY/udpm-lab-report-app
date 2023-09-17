package com.labreportapp.portalprojects.infrastructure.security;//package com.labreportapp.portalprojects.infrastructure.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfiguration {
//
//    @Autowired
//    private CustomAuthorizationManager customAuthorizationManager;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/admin/**").hasAuthority("admin")
//                .requestMatchers("/member/**").hasAuthority("member")
//                .requestMatchers("/stakeholder/**").hasAuthority("stakeholder")
//                .requestMatchers("/member/period/list-period/**").access(customAuthorizationManager)
//                .anyRequest()
//                .authenticated().and();
//        return http.build();
//    }
//
//}