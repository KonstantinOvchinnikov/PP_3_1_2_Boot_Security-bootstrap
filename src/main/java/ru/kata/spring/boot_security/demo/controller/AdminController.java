package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.util.MyValidator;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userService;
    private final RoleRepository roleRepository;
    private final MyValidator myValidator;

    @Autowired
    public AdminController(UserServiceImp userService, RoleRepository roleRepository, MyValidator myValidator) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.myValidator = myValidator;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "id", defaultValue = "0") long id,  @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("newUser", new User());
        model.addAttribute("authUser", userService.showUserByLogin(userDetails.getUsername()));
        model.addAttribute("listUsers", userService.showAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "/admin/index";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping
    public String createOrEditUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        myValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin";
        }
            userService.saveUser(user);
            return "redirect:/admin";
        }
    }
