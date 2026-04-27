package com.n11bootcamp.refreshtoken.service.impl;

import com.n11bootcamp.refreshtoken.model.RefreshToken;
import com.n11bootcamp.refreshtoken.model.User;
import com.n11bootcamp.refreshtoken.repository.RefreshTokenRepository;
import com.n11bootcamp.refreshtoken.exception.EntityNotFoundException;
import com.n11bootcamp.refreshtoken.exception.TokenException;
import com.n11bootcamp.refreshtoken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-validity}")
    private long refreshTokenValidity;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenValidity));
        refreshToken.setRevoked(false);
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Refresh token bulunamadı"));

        if (refreshToken.isRevoked()) {
            throw new TokenException("Refresh token iptal edilmiş");
        }

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenException("Refresh token süresi dolmuş");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public void revokeUserTokens(User user) {
        refreshTokenRepository.revokeAllUserTokens(user);
    }
}