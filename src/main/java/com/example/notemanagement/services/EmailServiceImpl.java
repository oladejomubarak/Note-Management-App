package com.example.notemanagement.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
@Async
@Slf4j
public class EmailServiceImpl implements EmailService{
        @Autowired
        private JavaMailSender javaMailSender;


        @Override
        public void send(String to, String email) throws MessagingException {
            try{
                MimeMessage mailMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
                mimeMessageHelper.setSubject("Confirm your email address");
                mimeMessageHelper.setTo(to);
                mimeMessageHelper.setFrom("olade@gmail.com");
                mimeMessageHelper.setText(email, true);
                javaMailSender.send(mailMessage);
            } catch (MessagingException e){
                log.info("Problem 1: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (MailException e){
                log.info("Problem 2" + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
