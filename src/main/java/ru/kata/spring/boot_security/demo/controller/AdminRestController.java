package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminRestController {
    private final UserServiceImp userService;

    @Autowired
    public AdminRestController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> indexAdmin() {
        return new ResponseEntity<>(userService.showAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<HttpStatus> createOrEdit(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<HttpStatus> delete(@RequestParam(value = "id") long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/show")
    public ResponseEntity<User> getUserById(@RequestParam(value = "id") long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
}
