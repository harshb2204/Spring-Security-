package com.example.SecurityApp.SecurityApplication.config;

import com.example.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Indicates that this class contains configuration for the Spring application context
@EnableWebSecurity
@RequiredArgsConstructor// Enables Spring Security for the application
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Configures the security filter chain, which defines the security rules for HTTP requests.
     * This method sets up authorization rules, form-based login, and returns a configured SecurityFilterChain.
     *
     *  httpSecurity a builder that allows configuration of web-based security
     * return SecurityFilterChain a configured filter chain that defines security settings
     * throws Exception in case of any configuration errors
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // Configuring which HTTP requests require authentication and authorization
        httpSecurity.authorizeHttpRequests(auth -> auth
                        // Allow anyone to access the /posts endpoint without authentication
                        .requestMatchers("/posts", "/error", "/auth/**").permitAll()
                        // Require the "ADMIN" role to access any URLs under /posts/
//                        .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                        // Require authentication for any other request that doesn't match the above patterns
                        .anyRequest().authenticated())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                // Enables form-based authentication with default settings
              // .formLogin(Customizer.withDefaults());

        // Builds the SecurityFilterChain based on the configurations provided
        return httpSecurity.build();
    }

    /**
     * Configures an in-memory user details service, which stores user information in memory.
     * This method creates two users: one with the role "USER" and another with the role "ADMIN".

     * return UserDetailsService a service for managing user details in memory
     */
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        // Creates a user with the username "harsh", password "harsh123", and role "USER"
//        UserDetails normalUser = User
//                .withUsername("harsh")
//                .password(passwordEncoder().encode("harsh123")) // Password is encoded using BCrypt
//                .roles("USER")
//                .build();
//
//        // Creates an admin user with the username "admin", password "admin", and role "ADMIN"
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin")) // Password is encoded using BCrypt
//                .roles("ADMIN")
//                .build();
//
//        // Returns an in-memory user details manager with the two users defined above
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }

    /**
     * Configures a password encoder bean that uses the BCrypt hashing algorithm.
     * BCrypt is a secure way to store passwords as it includes a salt and is computationally expensive to crack.

     * return PasswordEncoder a password encoder that uses BCrypt
     */



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
