package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Objects;

@Component
public class MyValidator implements Validator {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public MyValidator(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public boolean userInData (String username) {
        User myUser = userRepository.findUserByEmailIs(username);
        if (myUser == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userInData(user.getEmail()) && !Objects.equals(user.getId(), userService.showUserByLogin(user.getEmail()).getId())) {
            errors.rejectValue("email", "", "Пользователь с таким почтовым адресом уже существует");
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            errors.rejectValue("password", "", "Пароли не совпадают");
        }
    }
}
