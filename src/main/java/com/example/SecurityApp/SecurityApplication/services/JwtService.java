package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    // Injects the JWT secret key from application properties
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    /**
     * Converts the secret key string into a SecretKey object used for signing JWTs.
     *
     * @return the SecretKey object
     */
    private SecretKey getSecretKey(){
        // Convert the secret key string into bytes using UTF-8 encoding
        // Create a SecretKey for HMAC SHA using the byte array
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a JWT token for the given user.
     *
     * @param user the user for whom the token is being generated
     * @return a JWT token as a string
     */
    public String generateAccessToken(User user){
        // Create a JWT token with the specified claims and settings
        return Jwts.builder()
                // Set the subject of the token to the user's ID
                .subject(user.getId().toString())
                // Add custom claims: user's email and roles
                .claim("email", user.getEmail())
                .claim("roles", Set.of("ADMIN", "USER")) // Hardcoded roles for demonstration
                // Set the token's issued date to the current date and time
                .issuedAt(new Date())
                // Set the token's expiration date to 1 hour from the current time
                .expiration(new Date(System.currentTimeMillis()+ 1000*20))
                // Sign the token with the secret key
                .signWith(getSecretKey())
                // Build the compact string representation of the token
                .compact();
    }
    public String generateRefreshToken(User user){

        return Jwts.builder()

                .subject(user.getId().toString())



                .issuedAt(new Date())

                .expiration(new Date(System.currentTimeMillis()+  1000L *60*24*30*6))

                .signWith(getSecretKey())

                .compact();
    }

    /**
     * Extracts the user ID from the provided JWT token.
     *
     * @param token the JWT token from which to extract the user ID
     * @return the user ID extracted from the token
     */
    public Long getUserIdFromToken(String token){
        // Parse and validate the JWT token
        Claims claims = Jwts.parser()
                // Set the signing key used to verify the token
                .verifyWith(getSecretKey())
                // Build the JwtParser instance
                .build()
                // Parse the token and retrieve the claims
                .parseSignedClaims(token)
                // Extract the payload from the parsed token
                .getPayload();

        // Get the user ID from the claims subject and convert it to a Long
        return Long.valueOf(claims.getSubject());
    }
}
