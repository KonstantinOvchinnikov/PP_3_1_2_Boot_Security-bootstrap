package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.util.MyValidator;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserServiceImp userService;
    private final RoleRepository roleRepository;
    private final MyValidator myValidator;

    @Autowired
    public AuthController(UserServiceImp userService, RoleRepository roleRepository,MyValidator myValidator) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.myValidator = myValidator;
    }

    @GetMapping("/register")
    public String registrationPage( @ModelAttribute("user") User user) {
        return "/register";
    }
    @PostMapping("/register")
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult) {
        myValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/register";
        }
        user.setRoles(Collections.singleton(new Role(1L, "USER")));
        userService.saveUser(user);
        return "redirect:/login";
    }
}
