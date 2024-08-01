package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private  long id;
    private String role;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {}

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String ROLE_USER) {
        this.id = id;
        this.role = ROLE_USER;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return role;
    }
}
