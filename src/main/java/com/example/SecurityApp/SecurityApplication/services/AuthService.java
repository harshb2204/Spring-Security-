package com.example.SecurityApp.SecurityApplication.services;


import com.example.SecurityApp.SecurityApplication.dto.LoginDto;
import com.example.SecurityApp.SecurityApplication.dto.LoginResponseDto;
import com.example.SecurityApp.SecurityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        String accesstoken = jwtService.generateAccessToken(user);
        String refreshtoken = jwtService.generateRefreshToken(user);

        return new LoginResponseDto(user.getId(), accesstoken, refreshtoken);

    }

    public LoginResponseDto refreshToken(String refreshtoken) {

        Long userId = jwtService.getUserIdFromToken(refreshtoken);
        User user = userService.getUserById(userId);
        String accesstoken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(), accesstoken, refreshtoken);
    }
}
