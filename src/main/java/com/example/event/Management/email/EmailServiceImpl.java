package com.example.event.Management.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleEmail(EmailDetails emailDetails) {

        try {
            //creating simple mail message
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //set all required message
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessage());
            simpleMailMessage.setSubject(emailDetails.getSubject());

            //send message
            javaMailSender.send(simpleMailMessage);

            return "Mail Send Successfully";
        }
        catch (Exception e){
            e.printStackTrace(); // Print the stack trace for debugging
            return "Error while sending email with attachment: " + e.getMessage();
        }
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;

        try {
            MimeMessageHelper messageHelper1=new MimeMessageHelper(mimeMessage,true);
            messageHelper1.setFrom(sender);
            messageHelper1.setTo(emailDetails.getRecipient());
            messageHelper1.setText(emailDetails.getMessage());
            messageHelper1.setSubject(emailDetails.getSubject());
            //Adding the attachment
            FileSystemResource file=new FileSystemResource(new File(emailDetails.getAttachment()));
             messageHelper1.addAttachment(Objects.requireNonNull(file.getFilename()),file);
             //Send mail
            javaMailSender.send(mimeMessage);
            return "Mail Send Successfully";

        }
        catch (Exception e){
            e.printStackTrace(); // Print the stack trace for debugging
            return "Error while sending email with attachment: " + e.getMessage();

        }

    }
}
