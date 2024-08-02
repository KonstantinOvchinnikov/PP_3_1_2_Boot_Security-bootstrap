package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

public interface UserService {
    public List<User> showAllUsers();

    public void saveUser(User user);

    public void deleteUser(long id);

    public void updateUser(User user);

    public User showUserById(long id);

    public User showUserByLogin(String login);
}
