package com.hackers.freelancia.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hackers.freelancia.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    private final JwtAuthenticationFilter jwtFilter;
    private final UserService userService;

    // Constructeur pour l'injection des dépendances
    public SecurityConfig(JwtAuthenticationFilter jwtFilter, UserService userService) {
        this.jwtFilter = jwtFilter;
        this.userService = userService;
    }

    /**
     * Configure la chaîne de filtres de sécurité pour les requêtes HTTP.
     *
     * @param http l'objet HttpSecurity à configurer
     * @return la chaîne de filtres de sécurité configurée
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            ).userDetailsService(userService)
        .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configure le bean BCryptPasswordEncoder pour le chiffrement des mots de passe.
     *
     * @return une instance de BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure le bean AuthenticationManager pour l'authentification des utilisateurs.
     *
     * @param config la configuration d'authentification à utiliser pour créer l'AuthenticationManager
     * @return une instance d'AuthenticationManager
     * @throws Exception en cas d'erreur lors de la création de l'AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
