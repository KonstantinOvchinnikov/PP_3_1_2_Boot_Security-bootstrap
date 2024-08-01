package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findUserByEmailIs(String name);
   // void updateUserBy(User user);

}
