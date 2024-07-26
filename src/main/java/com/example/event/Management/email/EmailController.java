package com.example.event.Management.email;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor

public class EmailController {
    private final EmailService emailService;
    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    @PostMapping("/sendMail")
    public String sendSimpleEmail(@RequestBody EmailDetails emailDetails){
        return emailService.sendSimpleEmail(emailDetails);
    }
    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    @PostMapping("/sendEmailWithAttachment")
    public String sendEmailWithAttachment(@RequestBody EmailDetails emailDetails){
        return  emailService.sendEmailWithAttachment(emailDetails);
    }

}
