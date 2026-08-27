package com.taskflow.config;

import com.taskflow.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Temporarily disable security restrictions so all endpoints are accessible while debugging
        // Commented out JWT filter registration and role-based rules for now. Re-enable later when needed.
//        http.csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        // allow all requests during investigation
//                        .anyRequest().permitAll()
//                );

        // If you want to re-enable JWT auth later, uncomment the lines below and remove the permitAll above.
         http.csrf().disable()
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/api/auth/**", "/h2-console/**", "/").permitAll()
                         //Role-based authorization
                         .requestMatchers("/api/users/**").hasRole("ADMIN")
                         .requestMatchers("/api/projects/**").hasAnyRole("ADMIN", "PROJECT_MANAGER")
                         .requestMatchers("/api/tasks/**").hasAnyRole("ADMIN", "PROJECT_MANAGER", "DEVELOPER", "QA")
                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // allow H2 console frames
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
