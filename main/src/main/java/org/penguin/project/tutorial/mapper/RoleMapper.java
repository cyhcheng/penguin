package org.penguin.project.tutorial.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.penguin.project.tutorial.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @program: t3
 * @description:
 * @author: 程英华
 * @CreateDate: 2019-11-25 22:17
 * @UpdateUser:
 * @UpdateDate: 2019/11/25 22:17
 * @UpdateRemark:
 * @Version:
 */

@Mapper
public interface RoleMapper {

    List<Role> findAll();

    Optional<Role> findByName(String name);

    int insert(Role role);

    Set<Role> findByUserId(String id);
}
