package com.main.asm.security;

import com.main.asm.constant.AuthenticationProvider;
import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {


    private static final String[] PUBLIC_RESOURCES = {
            "/resources/**",
            "/static/**",
            "/bootstrapv5/**",
            "/css/**",
            "/img/**",
            "/js/**",
            "/register/**",
            "/",
            "/index",
            "/home",
            "/collection/**",
            "/products/**",
            "/place-order",
            "/login",
            "/logout",
            "/send-code",
            "/do-sendCode",
            "/check-code",
            "/do-checkCode",
            "/resetPassword",
            "/do-resetPassword",
            "/oauth2/**",
            "/c/**"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(c ->
                c
                        .requestMatchers("/users").hasAuthority("ADMIN")
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .anyRequest().authenticated());
        http.formLogin(c -> c.loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll());
        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/"));
        return http.build();


    }
}