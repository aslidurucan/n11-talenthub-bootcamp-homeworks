package com.n11bootcamp.refreshtoken.api;

import com.n11bootcamp.refreshtoken.dto.request.LoginRequest;
import com.n11bootcamp.refreshtoken.dto.request.RefreshTokenRequest;
import com.n11bootcamp.refreshtoken.dto.request.RegisterRequest;
import com.n11bootcamp.refreshtoken.dto.response.AuthResponse;
import com.n11bootcamp.refreshtoken.dto.response.RefreshTokenResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
public interface AuthControllerContract {

    @PostMapping("/register")
    ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request);

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/refresh")
    ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request);
}
