package com.jwt.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AppConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        // Create UserDetails objects with username, encoded password, and roles
        UserDetails user1 = User.builder()
                .username("harsh")
                .password(passwordEncoder().encode("abc"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("durgesh")
                .password(passwordEncoder().encode("abc"))
                .roles("ADMIN")
                .build();

        // Return an InMemoryUserDetailsManager with the created users
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

      @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
