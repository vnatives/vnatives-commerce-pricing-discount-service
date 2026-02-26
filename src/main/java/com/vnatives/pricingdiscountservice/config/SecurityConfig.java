package com.vnatives.pricingdiscountservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // 🔑 EXPLICIT matcher — THIS IS THE FIX
            .securityMatcher("/int/**", "/actuator/**")

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/int/**").permitAll()
                .anyRequest().denyAll()
            )

            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

}
