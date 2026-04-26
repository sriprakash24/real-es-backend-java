package com.realestate.config;

import com.realestate.security.JwtFilter;
import com.realestate.service.AuthService;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// ↓ Remove @RequiredArgsConstructor — no more constructor injection of JwtFilter
public class SecurityConfig {

    // ↓ Inject JwtFilter as method param, NOT as a field
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/properties/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/properties/featured").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/inquiries").hasRole("BUYER")
                        .requestMatchers("/api/inquiries/seller").hasAnyRole("SELLER", "AGENT")
                        .requestMatchers("/api/inquiries/buyer").hasRole("BUYER")
                        .requestMatchers("/api/inquiries/*/status").hasAnyRole("SELLER", "AGENT", "ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(AuthService authService) {
        return authService;
    }
}