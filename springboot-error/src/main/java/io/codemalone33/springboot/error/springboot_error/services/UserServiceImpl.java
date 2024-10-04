package io.codemalone33.springboot.error.springboot_error.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.codemalone33.springboot.error.springboot_error.models.User;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        this.users = List.of(
            new User(1L, "John", "JGonzalez@correo.com"),
            new User(2L, "Jane", "JRamirez@correo.com"),
            new User(3L, "Joe", "JSantos@correo.com"),
            new User(4L, "Sandra", "SLopez@correo.com"),
            new User(5L, "Mario", "MMendez@correo.com"));
    }
    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
        /* for (User u : users) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        } */
       
        return Optional.ofNullable(user);
    }

}
