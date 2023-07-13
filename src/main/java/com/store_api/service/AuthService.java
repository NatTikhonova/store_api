package com.store_api.service;

import com.store_api.model.*;
import com.store_api.model.request.AuthRequest;
import com.store_api.model.request.RegisterRequest;
import com.store_api.model.response.AuthResponse;
import com.store_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        try {
            User user = User.builder()
                    .fio(request.getFio())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.MANAGER)
                    .build();
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            AuthResponse response = AuthResponse.builder()
                    .answer("Регистрация прошла успешно!")
                    .token(token)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AuthResponse response = AuthResponse.builder()
                    .answer("Ошибка регистрации!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<AuthResponse> authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtService.generateToken(user);
            AuthResponse response = AuthResponse.builder()
                    .answer("Авторизация прошла успешно!")
                    .token(token)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            AuthResponse response = AuthResponse.builder()
                    .answer("Ошибка авторизации!")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }
}
