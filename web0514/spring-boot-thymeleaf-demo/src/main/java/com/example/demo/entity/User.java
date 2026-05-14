package com.example.demo.entity;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private Integer age;
    private String email;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 无参构造
    public User() {
    }

    // 全参构造（兼容你控制器里的new User()调用）
    public User(Long userId, String username, String password, String nickname, Integer age, String email, Integer status, LocalDateTime createTime, LocalDateTime updateTime) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.email = email;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter/Setter 方法
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}