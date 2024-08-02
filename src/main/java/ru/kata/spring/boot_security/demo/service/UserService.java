package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;

public interface UserService {
    List<User> showAllUsers();

    void saveUser(User user);

    void deleteUser(long id);

    User showUserById(long id);

    User showUserByLogin(String login);
}
