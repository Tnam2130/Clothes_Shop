package com.main.asm.service.impl;

import com.main.asm.constant.EmailType;
import com.main.asm.entity.Users;
import com.main.asm.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    private static final String EMAIL_WELCOME_SUBJECT = "WELCOME TO DEGREY";

    private static final String EMAIL_FORGOT_PASSWORD = "DEGREY ONLINE - NEW PASSWORD";


    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Users recipient,String to,String newPassword, EmailType emailType) {
        SimpleMailMessage message = new SimpleMailMessage();
        String content = null;
        String subject = null;
        switch (emailType){
            case WELCOME_TO_WEBSITE :
                subject = EMAIL_WELCOME_SUBJECT;
                content = "Love You!!!";
                break;
            case FORGOT_PASSWORD:
                subject = EMAIL_FORGOT_PASSWORD;
                content = "Dear " + recipient.getFullname()+ ", Your New Password Here : " + newPassword;
                break;
            default:
                subject = "Movie Online";
                content = "Email Wrong";
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }


}
