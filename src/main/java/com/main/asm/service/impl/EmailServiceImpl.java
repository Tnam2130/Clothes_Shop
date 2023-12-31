package com.main.asm.service.impl;

import com.main.asm.constant.EmailType;
import com.main.asm.entity.Users;
import com.main.asm.service.EmailService;
import com.main.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {


    private static final String EMAIL_WELCOME_SUBJECT = "WELCOME TO DEGREY";

    private static final String EMAIL_SEND_CODE = "DEGREY ONLINE - YOUR CODE FORGET PASSWORD";

    private Map<String,Users> codeUserMap = new HashMap<>();

    @Autowired
    UserService userService;

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to,String code, EmailType emailType) {
        SimpleMailMessage message = new SimpleMailMessage();
        String content = null;
        String subject = null;
        switch (emailType){
            case WELCOME_TO_WEBSITE :
                subject = EMAIL_WELCOME_SUBJECT;
                content = "Love You!!!";
                break;
            case EMAIL_SEND_CODE:
                subject = EMAIL_SEND_CODE;
                content = "Your Code Here : " + code;
                break;
            default:
                subject = "DEGREY ONLINE";
                content = "Email Wrong";
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);


    }
    @Override
    public String generateCode() {
        return String.valueOf((int) (Math.random() * ((999 - 100) + 1)) + 1000);
    }



    @Override
    public void sendCode(String email) {
        Optional.ofNullable(userService.findByEmail(email))
                .map(users -> {
                    String code = generateCode();
                    sendEmail(email, code, EmailType.EMAIL_SEND_CODE);
                    return codeUserMap.put(code, users);
                });
    }


    @Override
    public Users getUserByCode(String code){
        return codeUserMap.get(code);
    }

}