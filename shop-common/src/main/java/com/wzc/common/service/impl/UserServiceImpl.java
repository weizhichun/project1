package com.wzc.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wzc.common.constant.CommonConstant;
import com.wzc.common.entity.User;
import com.wzc.common.exception.BusinessException;
import com.wzc.common.mapper.UserMapper;
import com.wzc.common.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean register(String username, String password, String nickname) {
        User existUser = getByUsername(username);
        if (existUser != null) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname != null ? nickname : username);
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        int rows = userMapper.insert(user);
        log.info("用户注册: username={}, id={}", username, user.getId());
        return rows > 0;
    }

    @Override
    public User login(String username, String password) {
        User user = getByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() == null || user.getStatus() != CommonConstant.USER_STATUS_NORMAL) {
            throw new BusinessException("账号已被禁用");
        }
        log.info("用户登录: username={}, id={}", username, user.getId());
        return user;
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