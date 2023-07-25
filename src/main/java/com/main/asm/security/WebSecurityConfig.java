package com.main.asm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    private static final String[] PUBLIC_RESOURCES={
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
