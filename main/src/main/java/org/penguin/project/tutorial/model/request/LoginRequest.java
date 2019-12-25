package org.penguin.project.tutorial.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank
    private String username;

    private String email;

    private String mobile;

    @NotBlank
    private String password;
}
