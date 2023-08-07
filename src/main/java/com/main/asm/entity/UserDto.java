package com.main.asm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/*
 * DTO: Data Transform Object
 * sử dụng lớp UserDto để truyền dữ liệu giữa Controller và view */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Email không được để trống")
    @Email
    private String email;

    @NotEmpty(message = "Tên tài khoản không được để trống")
    private String username;

    @NotEmpty(message = "Password không được để trống")
    private String password;
}
