package com.plataforma.plataforma_pesquisas.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                // 🔓 ROTAS PÚBLICAS PRIMEIRO!
                .requestMatchers("/api/v1/convites/validar/**").permitAll()
                .requestMatchers("/api/v1/respostas-publicas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/formularios/**").permitAll()
                // 🔒 Rotas públicas do auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 🔐 Tudo do /api/v1 precisa de token
                .requestMatchers("/api/v1/**").authenticated()
                // Qualquer outra rota libera
                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2
                        -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(Keys.hmacShaKeyFor("CHAVE_SECRETA_ULTRA_SEGURA_32_BYTES_NO_MINIMO".getBytes()))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("perfil");

        authoritiesConverter.setAuthorityPrefix("PERFIL_");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

}
