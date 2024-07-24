package com.example.event.Management.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")

public class UserController {
    @GetMapping("/admin")
    ResponseEntity <String >adminEndpoint() {
        return  ResponseEntity.ok("i am admin end point");
    }

    @GetMapping("/user")
    ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("i am user");

    }
}
