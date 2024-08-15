package com.jwt.example.config;

import com.jwt.example.security.JwtHelper;
import com.jwt.example.models.JwtRequest;
import com.jwt.example.models.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        // Authenticate user
        authenticate(request.getEmail(), request.getPassword());

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        
        // Generate JWT token
        String token = jwtHelper.generateToken(userDetails);

        // Create response
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();
        
        // Log successful login
        logger.info("User {} logged in successfully", userDetails.getUsername());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for user: {}", email);
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        logger.error("Exception caught: {}", ex.getMessage());
        return new ResponseEntity<>("Credentials Invalid", HttpStatus.UNAUTHORIZED);
    }
}
