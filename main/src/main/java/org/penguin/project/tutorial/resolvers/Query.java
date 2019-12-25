package org.penguin.project.tutorial.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.exception.EntityNotFoundException;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *  http://localhost:8080/graphiql
 */
@Service
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final UserService userService;

    public List<User> listUsers() {
        List<User > allUsers = userService.findAll();
        return StreamSupport.stream(allUsers.spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUser(String username) throws EntityNotFoundException {
        return  userService.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

}
