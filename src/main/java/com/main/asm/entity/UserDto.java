package com.main.asm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.asm.constant.AuthenticationProvider;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/*
 * DTO: Data Transform Object
 * sử dụng lớp UserDto để truyền dữ liệu giữa Controller và view */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private Long id;

    @NotEmpty(message = "Email không được để trống")
    @Email
    private String email;

    @NotEmpty(message = "Tên tài khoản không được để trống")
    private String username;

    @NotEmpty(message = "Password không được để trống")
    private String password;

    private AuthenticationProvider authenticationProvider;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.getName());
        return Collections.singletonList(authority);
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
