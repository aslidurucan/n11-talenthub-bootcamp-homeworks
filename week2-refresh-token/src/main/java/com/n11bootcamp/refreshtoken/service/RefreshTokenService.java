package com.n11bootcamp.refreshtoken.service;

import com.n11bootcamp.refreshtoken.model.RefreshToken;
import com.n11bootcamp.refreshtoken.model.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void revokeUserTokens(User user);
}