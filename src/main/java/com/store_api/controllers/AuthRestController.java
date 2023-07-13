package com.store_api.controllers;

import com.store_api.model.request.AuthRequest;
import com.store_api.model.request.RegisterRequest;
import com.store_api.model.response.AuthResponse;
import com.store_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @RequestBody RegisterRequest request
    ){
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ){
        return authService.authenticate(request);
    }
}
