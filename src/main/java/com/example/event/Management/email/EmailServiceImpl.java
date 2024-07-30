package com.example.event.Management.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger=LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;

    @Override
    public String sendSimpleEmail(EmailDetails emailDetails) {

        try {
            //creating simple mail message
            logger.debug("Entering sendSimpleEmail() with email details:{}",emailDetails);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //set all required message
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setText(emailDetails.getMessage());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            logger.info("Set the info of email in simpleMailMessage:{}",simpleMailMessage);

            //send message
            javaMailSender.send(simpleMailMessage);
            logger.info("Email is send Successfully");
            return "Mail Send Successfully";
        }
        catch (Exception e){
            e.printStackTrace(); // Print the stack trace for debugging
            logger.error("Error is occur with attachment:{}",e.getMessage());
            return "Error while sending email with attachment: " + e.getMessage();
        }
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;

        try {
            logger.debug("sendEmailWithAttachment with emailDetails:{}",emailDetails);
            MimeMessageHelper messageHelper1=new MimeMessageHelper(mimeMessage,true);
            messageHelper1.setFrom(sender);
            messageHelper1.setTo(emailDetails.getRecipient());
            messageHelper1.setText(emailDetails.getMessage());
            messageHelper1.setSubject(emailDetails.getSubject());

            //Adding the attachment
            FileSystemResource file=new FileSystemResource(new File(emailDetails.getAttachment()));
             messageHelper1.addAttachment(Objects.requireNonNull(file.getFilename()),file);
            logger.info("Set the info of email in messageHelper1:{}",messageHelper1);
             //Send mail
            javaMailSender.send(mimeMessage);
            logger.info("Mail send successfully");
            return "Mail Send Successfully";

        }
        catch (Exception e){
            e.printStackTrace(); // Print the stack trace for debugging
            logger.error("Error while sending email with attachment:{}" , e.getMessage());
            return "Error while sending email with attachment: " + e.getMessage();

        }

    }
}
