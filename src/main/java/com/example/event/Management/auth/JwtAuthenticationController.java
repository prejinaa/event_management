//
//package com.example.event.Management.auth;
//
//
//import com.example.event.Management.config.JwtTokenUtil;
//
//import com.example.event.Management.event.EventRepo;
//
//import com.example.event.Management.user.Role;
//import com.example.event.Management.user.RoleRepo;
//import com.example.event.Management.user.User;
//import com.example.event.Management.user.UserRepo;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.Collections;
//import java.util.Objects;
//
//@RestController
//@RequiredArgsConstructor
//
//
//public class JwtAuthenticationController {
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenUtil jwtTokenUtil;
//    private final UserDetailsService userDetailsService;
//    private final UserRepo userRepo;
//    private final EventRepo eventRepo;
//    private final RoleRepo roleRepo;
//
//    private final PasswordEncoder passwordEncoder;
//    @PostMapping("/register")
//
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//       User  existingUser = userRepo.findByUsername(user.getUsername());
//        if (existingUser!=null) {
//            return ResponseEntity.badRequest().body("Username already exists");
//        }
//
//        User newuser = new User();
//        newuser.setUsername(user.getUsername());
//        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
//        // Fetch the default ROLE_USER
//        Role defaultRole = roleRepo.findByName("ROLE_USER");
//        if (defaultRole == null) {
//            // Create and save ROLE_USER if it does not exist
//            defaultRole = new Role();
//            defaultRole.setName("ROLE_USER");
//            roleRepo.save(defaultRole);
//        }
//        newuser.setRoles(Collections.singletonList(defaultRole));
//
//        userRepo.save(newuser);
//        return ResponseEntity.ok("User registered successfully");
//    }
//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
//
//        authenticate(authRequest.getUsername(), authRequest.getPassword());
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//
//        final String token = jwtTokenUtil.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthResponse(token));
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
//}