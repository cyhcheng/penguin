package org.penguin.project.tutorial.database;

import lombok.AllArgsConstructor;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataProvider implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
//        userService.insert(new User(...));

    }
}
