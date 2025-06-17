package com.exercice_sec.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    /**
     * @Bean
     *       SecurityFilterChain filterChain(HttpSecurity httpSecuritys) throws
     *       Exception {
     *       // configuration de la chaine de securité avec basic
     *       authenticatification
     *       // Ce filter chain nous permet d'agir sur notre configuration de
     *       sécurité
     *       return httpSecuritys.cors(Customizer.withDefaults()).csrf(csrf ->
     *       csrf.disable()).authorizeRequests(
     *       auth -> {
     *       auth.requestMatchers("/api/public/**").permitAll()
     *       .requestMatchers("/api/private/**").authenticated();
     *       }).httpBasic(Customizer.withDefaults()).build();
     * 
     *       }
     */

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // configuration de la chaine de securité avec basic authenticatification
        // Ce filter chain nous permet d'agir sur notre configuration de sécurité
        return http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/private/**").authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
