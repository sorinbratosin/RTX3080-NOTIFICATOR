package com.sorinbratosin.RTX3080NOTIFICATOR.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class RTX3080EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendMail(String to, String subject, String bodyText) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(bodyText);
        emailSender.send(mailMessage);
    }
}
