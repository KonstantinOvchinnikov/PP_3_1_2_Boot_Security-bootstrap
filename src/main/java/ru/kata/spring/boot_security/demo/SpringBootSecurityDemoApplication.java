package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;
import ru.kata.spring.boot_security.demo.util.MyValidator;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        initApplicationDefault(context);
    }

    public static void initApplicationDefault(ConfigurableApplicationContext context) {
        UserServiceImp userService = context.getBean(UserServiceImp.class);
        RoleService roleService = context.getBean(RoleService.class);
        MyValidator myValidator = context.getBean(MyValidator.class);

        roleService.saveIfNotExist("USER");
        roleService.saveIfNotExist("ADMIN");

        User user = new User();
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRole("USER"));
        user.setName("User");
        user.setSurname("User");
        user.setAge(10);
        user.setEmail("user@mail.ru");
        user.setPassword("user");
        user.setRoles(roles);
        if (!myValidator.userInData(user.getEmail())) {
            userService.saveUser(user);
        }

        User admin = new User();
        admin.setName("Admin");
        admin.setSurname("Admin");
        admin.setAge(100);
        admin.setEmail("admin@mail.ru");
        admin.setPassword("admin");
        admin.setRoles(roleService.findAll());
        if (!myValidator.userInData(admin.getEmail())) {
            userService.saveUser(admin);
        }



    }
}
