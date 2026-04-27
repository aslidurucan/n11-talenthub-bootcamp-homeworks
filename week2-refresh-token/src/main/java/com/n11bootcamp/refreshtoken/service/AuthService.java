package com.n11bootcamp.refreshtoken.service;

import com.n11bootcamp.refreshtoken.dto.request.LoginRequest;
import com.n11bootcamp.refreshtoken.dto.request.RegisterRequest;
import com.n11bootcamp.refreshtoken.dto.request.RefreshTokenRequest;
import com.n11bootcamp.refreshtoken.dto.response.AuthResponse;
import com.n11bootcamp.refreshtoken.dto.response.RefreshTokenResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    RefreshTokenResponse refresh(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);
}