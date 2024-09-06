package com.example.SecurityApp.SecurityApplication.advices;

import com.example.SecurityApp.SecurityApplication.exceptions.ResourceNotFound;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFound exception){
        ApiError apiError = new ApiError(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException authenticationException){
        ApiError apiError = new ApiError(authenticationException.getLocalizedMessage(),HttpStatus.UNAUTHORIZED );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException jwtException){
        ApiError apiError = new ApiError(jwtException.getLocalizedMessage(),HttpStatus.UNAUTHORIZED );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
