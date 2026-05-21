package com.wzc.common.service;

import com.wzc.common.entity.User;

public interface UserService {

    User getById(Long id);

    boolean updateProfile(Long id, String nickname, String email, String phone);

    boolean updateAvatar(Long id, String avatarUrl);
}