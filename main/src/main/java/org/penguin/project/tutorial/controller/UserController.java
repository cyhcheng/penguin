package org.penguin.project.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.service.UserService;
import org.penguin.project.tutorial.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(path = "/list")
    public List<User> all() {
//  ResponseEntity return new ResponseEntity>(getUsers(), HttpStatus.OK);
        return userService.findAll();
    }

    @PostMapping("/create")
    public User createUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
//            "redirect:list";
        }
        int operatedRow = userService.insert(user);
        log.info(operatedRow == 1 ? "Successfully create a new user" : "Failure create a new user");
        return user;
    }
}
