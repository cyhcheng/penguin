package org.penguin.project.tutorial.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private final String type = "Bearer";

    private String id;

    private String username;

    private String email;

    private List<String> roles;
}
