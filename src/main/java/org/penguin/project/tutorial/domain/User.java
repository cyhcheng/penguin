package org.penguin.project.tutorial.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.penguin.project.tutorial.enums.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * @program: t3
 * @description:
 * @author: 程英华
 * @CreateDate: 2019-11-21 17:01
 * @UpdateUser:
 * @UpdateDate: 2019/11/21 17:01
 * @UpdateRemark:
 * @Version:
 */

// @JsonCreator
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseDomain {

    private static final long serialVersionUID = -25541114590362230L;

//    @NotEmpty
    private String id;

    @NotBlank
    private String fullname;

    @NotBlank
    private String account;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
//    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$")
    private String mobile;

    @NotNull
    private Gender gender;

    private String description;

}
