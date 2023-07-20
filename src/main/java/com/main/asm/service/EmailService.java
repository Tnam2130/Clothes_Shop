package com.main.asm.service;


import com.main.asm.constant.EmailType;
import com.main.asm.entity.Users;

public interface EmailService {

    void sendEmail(Users recipient,String to,String newPassword,EmailType emailType);



}
