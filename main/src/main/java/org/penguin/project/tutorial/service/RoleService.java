package org.penguin.project.tutorial.service;

import lombok.AllArgsConstructor;
import org.penguin.project.tutorial.mapper.RoleMapper;
import org.penguin.project.tutorial.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    public Optional<Role> findByName(String name) {
        return roleMapper.findByName(name);
    }

    public boolean insert(Role role) {
        return roleMapper.insert(role) == 1;
    }

    public Set<Role> findByUserId(String id) {
        return roleMapper.findByUserId(id);
    }
}
