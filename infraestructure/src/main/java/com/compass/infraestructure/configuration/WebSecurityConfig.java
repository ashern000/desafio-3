package com.compass.infraestructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/users/reset-password").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/registration").permitAll()
                                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/products").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/products").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/products").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/sales").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/sales").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/sales/report/filter_by_date").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE, "/sales").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/sales").hasRole("USER")
                                .requestMatchers(HttpMethod.PUT, "/sales").hasRole("USER")
                                .requestMatchers( "/swagger-ui.html").permitAll()
                                .requestMatchers( "/swagger-ui/index.html").permitAll()
                                .requestMatchers( "/swagger-ui/**").permitAll()
                                .requestMatchers( "/v3/api-docs/**").permitAll()
                                .requestMatchers( "/swagger-resources/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
