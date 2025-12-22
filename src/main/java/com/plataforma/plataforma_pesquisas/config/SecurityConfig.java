package com.plataforma.plataforma_pesquisas.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CorsFilter corsFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                // CORS antes do JWT
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm
                        -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/formularios/publico").permitAll()
                .requestMatchers("/api/v1/acesso/**").permitAll()
                .requestMatchers("/api/v1/respostas/respostas-publicas").permitAll()
                // endpoints públicos
                .requestMatchers(
                        "/api/auth/**",
                        "/error"
                ).permitAll()
                .requestMatchers("/api/v1/usuarios/**")
                .hasAuthority("USUARIOS_CADASTRO")
                .requestMatchers("/api/v1/empresas/**")
                .hasAuthority("EMPRESAS_CADASTRO")
                .requestMatchers("/api/v1/perfis/**")
                .hasAuthority("PERFIS_CADASTRO")
                // todo o resto protegido
                .anyRequest().authenticated()
                )
                // JWT antes do UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
