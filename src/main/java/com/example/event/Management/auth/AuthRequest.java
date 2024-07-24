package com.example.event.Management.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
@RequiredArgsConstructor
//here we send the request to jwt

public class AuthRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 5926468583005150707L;

        private String username;
        private String password;
}
