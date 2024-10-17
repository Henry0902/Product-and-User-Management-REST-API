package com.project.repository;

import org.springframework.data.repository.CrudRepository;

import com.project.table.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {
    Optional<UserInfo> findByUsername(String username);


    long countByUsername(String userName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
