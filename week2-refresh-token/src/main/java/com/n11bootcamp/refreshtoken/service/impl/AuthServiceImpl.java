package com.n11bootcamp.refreshtoken.service.impl;

import com.n11bootcamp.refreshtoken.auth.TokenManager;
import com.n11bootcamp.refreshtoken.dto.request.LoginRequest;
import com.n11bootcamp.refreshtoken.dto.request.RefreshTokenRequest;
import com.n11bootcamp.refreshtoken.dto.request.RegisterRequest;
import com.n11bootcamp.refreshtoken.dto.response.AuthResponse;
import com.n11bootcamp.refreshtoken.dto.response.RefreshTokenResponse;
import com.n11bootcamp.refreshtoken.model.RefreshToken;
import com.n11bootcamp.refreshtoken.model.Role;
import com.n11bootcamp.refreshtoken.model.User;
import com.n11bootcamp.refreshtoken.repository.UserRepository;
import com.n11bootcamp.refreshtoken.exception.BusinessException;
import com.n11bootcamp.refreshtoken.exception.EntityNotFoundException;
import com.n11bootcamp.refreshtoken.service.AuthService;
import com.n11bootcamp.refreshtoken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Bu kullanıcı adı zaten kullanılıyor");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Bu email zaten kullanılıyor");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                roles
        );

        userRepository.save(user);

        String accessToken = tokenManager.generateAccessToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));

        refreshTokenService.revokeUserTokens(user);

        String accessToken = tokenManager.generateAccessToken(user.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(
                request.getRefreshToken());

        String accessToken = tokenManager.generateAccessToken(
                refreshToken.getUser().getUsername());

        return new RefreshTokenResponse(accessToken);
    }

    @Override
    public void logout(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(
                request.getRefreshToken());

        refreshTokenService.revokeUserTokens(refreshToken.getUser());
    }
}