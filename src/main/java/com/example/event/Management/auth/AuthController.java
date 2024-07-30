package com.example.event.Management.auth;
import com.example.event.Management.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        logger.info("Entering registerUser () method with  user:{}", user.getUsername());
        logger.info("User register ");
        return authService.registerUser(user);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        logger.info("Entering createAuthenticationToken() with requester:{}", authRequest.getUsername());
        logger.info("successfully creation ");
        return authService.createAuthenticationToken(authRequest);
    }
}


