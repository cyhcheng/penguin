package org.penguin.project.tutorial.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.penguin.project.tutorial.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * @program: t3
 * @description:
 * @author: 程英华
 * @CreateDate: 2019-11-21 17:22
 * @UpdateUser:
 * @UpdateDate: 2019/11/21 17:22
 * @UpdateRemark:
 * @Version:
 */
@Mapper
public interface UserMapper {

    List<User> findAll();

    int insert(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    boolean existByUsername(String username);

    boolean existByEmail(String email);
}
