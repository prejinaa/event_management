package com.example.event.Management.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor


public class EmailController {
    Logger logger=LoggerFactory.getLogger(EmailController.class);
    private final EmailService emailService;


    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    @PostMapping("/sendMail")
    public String sendSimpleEmail(@RequestBody EmailDetails emailDetails)
    {
        logger.trace("Entering sendSimple() with emailDetails:{}",emailDetails);
        logger.info("Send emila successfully");
        return emailService.sendSimpleEmail(emailDetails);
    }

    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    @PostMapping("/sendEmailWithAttachment")
    public String sendEmailWithAttachment(@RequestBody EmailDetails emailDetails)
    {
        logger.trace("Entering sendEmailWithAttachment() with emailDetails:{}",emailDetails);
        logger.info("Send emilaWithAttachment successfully");
        return  emailService.sendEmailWithAttachment(emailDetails);
    }

}
