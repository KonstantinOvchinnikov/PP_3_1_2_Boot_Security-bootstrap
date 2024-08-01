package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collections;
import java.util.List;

@Service("customUserService")
public class UserServiceImp implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }


    public void saveUser(User user) {

        user.setRoles(Collections.singleton(new Role(1L)));
        user.setPassword(user.getPassword());
        userRepository.save(user);

    }

    public void deleteUser(long id) {
            userRepository.deleteById(id);
    }


    public void updateUser(User user) {
        user.setRoles(Collections.singleton(new Role(1L)));
        userRepository.save(user);
    }
    public User showUserById(long id) {
        String hql = "select u from User u where id=:id";
        return em.createQuery(hql, User.class).setParameter("id", id).getSingleResult();
    }

    public User showUserByLogin(String login) {
        String hql = "select u from User u where email=:email";
        return em.createQuery(hql, User.class).setParameter("email", login).getSingleResult();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findUserByEmailIs(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return myUser;
    }
}
