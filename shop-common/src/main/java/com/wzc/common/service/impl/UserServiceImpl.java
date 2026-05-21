package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wzc.common.entity.User;
import com.wzc.common.mapper.UserMapper;
import com.wzc.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean updateProfile(Long id, String nickname, String email, String phone) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, id);
        if (nickname != null && !nickname.trim().isEmpty()) {
            wrapper.set(User::getNickname, nickname);
        }
        if (email != null && !email.trim().isEmpty()) {
            wrapper.set(User::getEmail, email);
        }
        if (phone != null && !phone.trim().isEmpty()) {
            wrapper.set(User::getPhone, phone);
        }
        int rows = userMapper.update(null, wrapper);
        log.info("用户信息更新: userId={}, affected={}", id, rows);
        return rows > 0;
    }

    @Override
    public boolean updateAvatar(Long id, String avatarUrl) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, id)
                .set(User::getAvatar, avatarUrl);
        int rows = userMapper.update(null, wrapper);
        log.info("用户头像更新: userId={}, avatar={}, affected={}", id, avatarUrl, rows);
        return rows > 0;
    }
}