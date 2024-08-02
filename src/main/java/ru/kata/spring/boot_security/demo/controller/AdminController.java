package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "id", defaultValue = "0") long id) {
        if (id == 0) {
            model.addAttribute("list", userService.showAllUsers());
        } else {
            model.addAttribute("list", userService.showUserById(id));
        }
        return "/head";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/update";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public ModelAndView createOrEditUser(@RequestParam(name = "id", defaultValue = "0") Long id) {
        User user;
        if (id == 0) {
            user = new User("Name", "Surname", 0, "email@name.host", "Password");
        } else {
            user = userService.showUserById(id);
        }
        ModelAndView mav = new ModelAndView("/update");
        mav.addObject("user", user);
        List<Role> roles = roleRepository.findAll();
        mav.addObject("roles", roles);
        return mav;
    }

    @PostMapping("/update")
    public String userupdate(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/show")
    public String show(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.showUserById(id));
        return "/show";
    }
}
