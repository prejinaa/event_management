package com.example.event.Management.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegisterRequest {

    @NotBlank
    @Email(message = "Please ! enter a valid email  address")
    private String username;

    @NotNull
    @Size(min=3 ,max = 15)
    private  String password;
}
