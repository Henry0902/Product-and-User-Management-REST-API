package com.project.model.mapper;

import com.project.model.dto.UserInfoDto;
import com.project.table.UserInfo;

public class UserMapper {

    public UserInfoDto toDto(UserInfo userInfo) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(userInfo.getId());
        dto.setUsername(userInfo.getUsername());
        dto.setFullName(userInfo.getFullName());
        dto.setStatus(userInfo.getStatus());
        dto.setGroupId(userInfo.getGroupId());
        dto.setCreateTime(userInfo.getCreateTime());
        dto.setCreateBy(userInfo.getCreateBy());
        dto.setEmail(userInfo.getEmail());
        return dto;
    }

    public UserInfo toEntity(UserInfoDto dto) {
        UserInfo user = new UserInfo();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFullName(dto.getFullName());
        user.setStatus(dto.getStatus());
        user.setGroupId(dto.getGroupId());
        user.setCreateTime(dto.getCreateTime());
        user.setCreateBy(dto.getCreateBy());
        user.setEmail(dto.getEmail());
        return user;
    }
}
