package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void saveIfNotExist(Role role) {
        Role myRole = roleRepository.findByRole(role.getRole());
        if (myRole == null) {
            roleRepository.save(role);
        }
    }

    @Transactional
    public Set<Role> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Transactional
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
