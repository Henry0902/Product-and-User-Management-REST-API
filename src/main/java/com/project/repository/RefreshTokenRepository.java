package com.project.repository;

import com.project.table.RefreshToken;
import com.project.table.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByuserInfo_id(Long userInfo_id);


    @Modifying
    int deleteByUserInfo(UserInfo userInfo);
}
