package com.kaceper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Kaceper on 2017-11-29.
 */


@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email){
        mailSender.send(email);
    }
}
