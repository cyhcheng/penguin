package org.penguin.project.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/list")
    public List<User> all() {
//  ResponseEntity      return new ResponseEntity>(getUsers(), HttpStatus.OK);
        return userService.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Valid User user) {
        int operatedRow = userService.insert(user);
        log.info(operatedRow == 1 ? "Successfully create a new user" : "Failure create a new user");
        return user;
    }

    @RequestMapping(value = "/logmeout", method = RequestMethod.POST)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
