package com.example.event.Management.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor

public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        return service.registerUser(registerRequest);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authentication(
            @RequestBody AuthRequest authRequest
    ) throws Exception {
        return ResponseEntity.ok(service.createAuthenticationToken(authRequest));
    }
}
