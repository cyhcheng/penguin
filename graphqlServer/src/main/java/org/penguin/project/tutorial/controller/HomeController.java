package org.penguin.project.tutorial.controller;

import org.penguin.project.tutorial.dao.UserMapper;
import org.penguin.project.tutorial.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final UserMapper userMapper;

    public HomeController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/user/list")
    public List<User> all() {
        return userMapper.findAll();
    }
}
