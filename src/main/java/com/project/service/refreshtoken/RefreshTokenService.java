package com.project.service.refreshtoken;

import com.project.exception.exception.TokenRefreshException;
import com.project.repository.RefreshTokenRepository;
import com.project.repository.UserInfoRepository;
import com.project.table.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userInfo_id) {
        Optional<RefreshToken> isAlreadyExist = refreshTokenRepository.findByuserInfo_id(userInfo_id);

        isAlreadyExist.ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserInfo(userInfoRepository.findById(userInfo_id).get());
        refreshToken.setExpirationDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpirationDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired!. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userInfo_id) {
        return refreshTokenRepository.deleteByUserInfo(userInfoRepository.findById(userInfo_id).get());
    }

}
