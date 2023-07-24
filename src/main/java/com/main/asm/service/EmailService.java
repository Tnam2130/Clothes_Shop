package com.main.asm.service;


import com.main.asm.constant.EmailType;
import com.main.asm.entity.Users;

public interface EmailService {

    void sendEmail(String to,String code,EmailType emailType);

    void sendCode(String email);

    String generateCode();
    Users getUserByCode(String code);
}
