package org.penguin.project.tutorial.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.enums.Gender;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final UserService userService;

    private final PasswordEncoder bcryptPasswordEncoder;

    @Transactional
    public User createUser(String fullname, String username, String email, String mobile) {
        log.info("Terry -mutation create user received parameters> {},{},{},{}", fullname,username,email,mobile);
        User user = User.builder().id(UUID.randomUUID().toString()).fullname(fullname).username(username).encryptedPassword(bcryptPasswordEncoder.encode("123456789")).email(email).mobile(mobile).gender(Gender.Male).build();
        int successfulCode = userService.insert(user);
        if (successfulCode == 1) {
            return user;
        } else {
            return null;
        }
    }
}
