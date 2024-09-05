package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImp implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Transactional
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User findUserById(long id) {
        String hql = "select u from User u where id=:id";
        return em.createQuery(hql, User.class).setParameter("id", id).getSingleResult();
    }

    @Transactional
    public User findUserByEmail(String login) {
        String hql = "select u from User u where email=:email";
        return em.createQuery(hql, User.class).setParameter("email", login).getSingleResult();
    }

    @Transactional
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User myUser = userRepository.findUserByEmailIs(username);
        if (myUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
//        Hibernate.initialize(myUser.getRoles());
        return myUser;
    }
}
