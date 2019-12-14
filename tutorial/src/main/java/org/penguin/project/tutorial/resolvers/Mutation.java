package org.penguin.project.tutorial.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final UserService userService;

    @Transactional
    public User createUser(String fullname, String account, String email, String mobile) {
        User user = User.builder().id(UUID.randomUUID().toString()).fullname(fullname).account(account).password("123456789").email(email).mobile(mobile).build();
        int successfulCode = userService.insert(user);
        if (successfulCode == 1) {
            return user;
        } else {
            return null;
        }
    }
}
