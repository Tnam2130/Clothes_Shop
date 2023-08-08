package com.main.asm.security;

import com.main.asm.security.oauth.CustomOAuth2User;
import com.main.asm.security.oauth.CustomOAuth2UserService;
import com.main.asm.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    CustomOAuth2UserService oAuth2UserService;

    @Autowired
    UserService userService;

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
                                .defaultSuccessUrl("/")
                                .permitAll()
                                .and()
                                .oauth2Login().loginPage("/login").userInfoEndpoint().userService(oAuth2UserService)
                                .and()
                                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        DefaultOidcUser oauthUser= (DefaultOidcUser) authentication.getPrincipal();
                                        String email= oauthUser.getEmail();
                                        System.out.println(email);
                                        userService.processOAuthPostLogin(email);
                                        response.sendRedirect("/");
                                    }
                                })
                                .failureUrl("/login?error");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        http.logout(c -> c.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/"));
        return http.build();
    }
}