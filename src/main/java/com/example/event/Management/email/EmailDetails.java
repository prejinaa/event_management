package com.example.event.Management.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class EmailDetails {

    private String recipient;
    private String message;
    private String subject;
    private String attachment;
}
