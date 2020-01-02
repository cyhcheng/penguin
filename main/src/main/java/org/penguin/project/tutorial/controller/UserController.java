package org.penguin.project.tutorial.controller;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.model.response.BaseWebResponse;
import org.penguin.project.tutorial.service.UserService;
import org.penguin.project.tutorial.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

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

    // RxJava 2
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse>> deleteUser(@PathVariable(value = "username") String username) {
        return userService.deleteUser(username)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse<User>>> getUserDetail(@PathVariable(value = "username") String username) {
        return userService.getUserDetail(username)
                .subscribeOn(Schedulers.io())
                .map(userResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(userResponse)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse<List<User>>>> getAllUsers(@RequestParam(value = "limit", defaultValue = "5") int limit, @RequestParam(value = "page", defaultValue = "0") int page) {
        return userService.getAllUsers(limit, page)
                .subscribeOn(Schedulers.io())
                .map(userResponses -> ResponseEntity.ok(BaseWebResponse.successWithData(userResponses)));
    }

//    private List<User> toUserWebResponseList(List<User> userResponseList) {
//        return userResponseList
//                .stream()
//                .map(this::toUserWebResponse)
//                .collect(Collectors.toList());
//    }
//
//    private User toUserWebResponse(User userResponse) {
//        User userWebResponse = new User();
//        BeanUtils.copyProperties(userResponse, userWebResponse);
//        return userWebResponse;
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse>> updateUser(@PathVariable(value = "username") String username, @RequestBody User user) {
        return userService.updateUser(user)
                .subscribeOn(Schedulers.io())
                .toSingle(() -> ResponseEntity.ok(BaseWebResponse.successNoData()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<BaseWebResponse>> addUser(@RequestBody User addUserWebRequest) {
        return userService.addUser(addUserWebRequest)
                .subscribeOn(Schedulers.io())
                .map(s -> ResponseEntity.created(URI.create("/api/users/" + s)).body(BaseWebResponse.successNoData()));
    }

}
