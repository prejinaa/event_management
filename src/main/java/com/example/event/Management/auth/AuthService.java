package com.example.event.Management.auth;

import com.example.event.Management.config.JwtTokenUtil;

import com.example.event.Management.user.Role;
import com.example.event.Management.user.RoleRepo;
import com.example.event.Management.user.User;
import com.example.event.Management.user.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class AuthService {

    Logger logger= LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(User user) {
        logger.debug("Entering the RegisterUser() with  user:{}",user.getUsername());
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null)
        {
           logger.warn("Username already exist with name:{}",user.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        // Fetch the default ROLE_USER
        Role defaultRole = roleRepo.findByName("ROLE_USER");
        if (defaultRole == null)
        {
            logger.debug("Default role ROLE_USE not found,create new one");
            // Create and save ROLE_USER if it does not exist
            defaultRole = new Role();
            defaultRole.setName("ROLE_USER");
            roleRepo.save(defaultRole);
        }

        newUser.setRoles(Collections.singletonList(defaultRole));
        userRepo.save(newUser);
        logger.info("User register Successfully with name:{}",user.getUsername());
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) throws Exception {
        logger.debug("Entering Authenticate user name :{}",authRequest.getUsername());
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        logger.info("Authenticate successful with the user:{}",authRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        logger.debug("Authenticate user with:{}",username);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.error("USER_DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            logger.error("INVALID_CREDENTIALS");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

