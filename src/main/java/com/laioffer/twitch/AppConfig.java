package com.laioffer.twitch;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.sql.DataSource;

// @Configuration annotation indicates this class is a source of bean definitions for Spring applications
    // application.yml is used for external config eg. database conn, server ports, etc.
    // AppConfig focuses on internal app logic & bean def
@Configuration
public class AppConfig {
    // The Twitch API requires authentication because it enforces strict access control
        // This is why https://api.twitch.tv/helix/games/top requires authentication
    // Your local endpoint does not require authentication because of the permitAll() configuration in your SecurityFilterChain
        // This is why http://localhost:8080/game does NOT require authentication

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeHttpRequests(auth -> auth
                // front-end permission granted
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/*.json", "/*.png", "/static/**").permitAll()
                // back-end permission granted
                .requestMatchers(HttpMethod.POST, "/login", "/register", "/logout").permitAll()
                .requestMatchers(HttpMethod.GET, "/recommendation", "/game", "/search").permitAll()
                // any requests not in the whiteList above will need authentication
                .anyRequest().authenticated()
        )
        .exceptionHandling()
        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        .and()
        .formLogin()
        .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and()
        .logout()
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));

        return http.build();
    }

    @Bean
    UserDetailsManager users(DataSource datasource) {
        // JdbcUserDetailsManager is a Spring security class that stores
        // user details(username, password, roles) in DB
        return new JdbcUserDetailsManager(datasource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
