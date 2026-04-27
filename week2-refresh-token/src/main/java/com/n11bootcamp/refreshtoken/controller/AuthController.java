package com.n11bootcamp.refreshtoken.controller;

import com.n11bootcamp.refreshtoken.api.AuthControllerContract;
import com.n11bootcamp.refreshtoken.dto.request.LoginRequest;
import com.n11bootcamp.refreshtoken.dto.request.RefreshTokenRequest;
import com.n11bootcamp.refreshtoken.dto.request.RegisterRequest;
import com.n11bootcamp.refreshtoken.dto.response.AuthResponse;
import com.n11bootcamp.refreshtoken.dto.response.RefreshTokenResponse;
import com.n11bootcamp.refreshtoken.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthControllerContract {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refresh(RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @Override
    public ResponseEntity<Void> logout(RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }
}