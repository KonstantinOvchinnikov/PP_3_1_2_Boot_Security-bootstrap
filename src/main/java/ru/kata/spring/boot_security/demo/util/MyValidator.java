package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.transaction.Transactional;
import java.util.Objects;

@Component
public class MyValidator implements Validator {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserServiceImp userService;

    @Autowired
    public MyValidator(UserRepository userRepository, RoleRepository roleRepository, UserServiceImp userService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }
    @Transactional
    public boolean userInData (String username) {
        User myUser = userRepository.findUserByEmailIs(username);
        return myUser != null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    @Transactional
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        // Если идет добавление нового пользователя, а не изменение существующего.
        if (userInData(user.getEmail()) && !Objects.equals(user.getId(), userService.findUserByEmail(user.getEmail()).getId())) {
            errors.rejectValue("email", "", "Пользователь с таким почтовым адресом уже существует");
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordConfirm())) {
            errors.rejectValue("password", "", "Пароли не совпадают");
        }
    }
}
