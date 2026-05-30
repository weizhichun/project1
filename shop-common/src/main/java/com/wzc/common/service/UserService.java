package com.wzc.common.service;

import com.wzc.common.entity.User;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    boolean register(String username, String password, String nickname);

    User login(String username, String password);

    boolean updateProfile(Long id, String nickname, String email, String phone);

    boolean updateAvatar(Long id, String avatarUrl);
}