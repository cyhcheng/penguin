package org.penguin.project.tutorial.dao;

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

    Optional<User> findByAccountOrEmail(String account, String email);

    Optional<User> findByAccount(String account);

    boolean existByAccount(String user);

    boolean existByEmail(String email);
}
