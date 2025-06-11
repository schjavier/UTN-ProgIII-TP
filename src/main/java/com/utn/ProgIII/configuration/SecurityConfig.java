package com.utn.ProgIII.configuration;

import com.utn.ProgIII.security.JwtFilter;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtFilter jwtFilter,
                                                   UserDetailServiceImpl userDetailsService) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/user/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/product/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/product/**").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/productSupplier/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PATCH,  "/productSupplier/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/productSupplier/**").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/supplier/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/supplier/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/supplier/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/supplier/**").hasRole("EMPLOYEE")

                        .requestMatchers(HttpMethod.GET, "/misc/dollar").hasRole("MANAGER")


                        .requestMatchers(HttpMethod.GET,"/docs/**","/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**").permitAll()

                        .anyRequest().authenticated()

                )
                .authenticationManager(authenticationManager(userDetailsService, passwordEncoder()))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailServiceImpl userDetailsService, PasswordEncoder passwordEncoder)throws Exception{

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);

    }

    @Bean
    static RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MANAGER")
                .role("MANAGER").implies("EMPLOYEE")
                .build();
    }

}

