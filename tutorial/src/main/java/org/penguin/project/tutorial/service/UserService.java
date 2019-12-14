package org.penguin.project.tutorial.service;

import org.penguin.project.tutorial.dao.UserMapper;
import org.penguin.project.tutorial.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Optional.empty();
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    public Optional<User> findByAccountOrEmail(String account, String email) {
        return userMapper.findByAccountOrEmail(account, email);
    }

    public Optional<User> findByAccount(String account) {
        return userMapper.findByAccount(account);
    }

    public boolean existByAccount(String user) {
        return userMapper.existByAccount(user);
    }

    public boolean existByEmail(String email) {
        return userMapper.existByEmail(email);
    }
}
