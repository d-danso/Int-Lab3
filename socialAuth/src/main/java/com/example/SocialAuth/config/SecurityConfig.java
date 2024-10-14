package com.example.SocialAuth.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
public class SecurityConfig {


    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf( c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests( auth ->
                        auth
                                .requestMatchers(new AntPathRequestMatcher("/index.html"),
                                        new AntPathRequestMatcher("/home.html"),
                                        new AntPathRequestMatcher("/webjars/**"),
                                        new AntPathRequestMatcher("/ error")).permitAll()
                                .anyRequest().authenticated()
         )
                            .logout( l -> l.logoutSuccessUrl("/index.html").
                             permitAll() )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/");  // Redirect to home on successful login
                        })
                );
         return http.build();
         }


         }