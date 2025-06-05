package com.utn.ProgIII.configuration;

import com.utn.ProgIII.security.JwtFilter;
import com.utn.ProgIII.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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

                        .requestMatchers(HttpMethod.POST, "/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/product/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/productSupplier/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/productSupplier/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/productSupplier/**").hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers(HttpMethod.POST, "/supplier/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/supplier/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/supplier/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/supplier/**").hasAnyRole("ADMIN", "EMPLOYEE")

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

}

