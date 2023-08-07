package com.main.asm.security;

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
        http.formLogin(c -> {
            try {
                c.loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .and()
                        .oauth2Login(oc -> oc.loginPage("/login").userInfoEndpoint(ui -> ui.userService(oauth2LoginHandler()).oidcUserService(oidcLoginHandler())));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/"));
        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            OidcUserService delegate = new OidcUserService();
            return delegate.loadUser(userRequest);
        };
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            return delegate.loadUser(userRequest);
        };
    }


}
