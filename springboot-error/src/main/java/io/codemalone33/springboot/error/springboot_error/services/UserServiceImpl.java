package io.codemalone33.springboot.error.springboot_error.services;

import java.util.List;

import org.springframework.stereotype.Service;

import io.codemalone33.springboot.error.springboot_error.exceptions.UserNotFoundException;
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
    public User findById(Long id) {
        User user = null;
        for (User u : users) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }
        if (user == null) {
            throw new UserNotFoundException("Error el usuario no existe");
        }
        return user;
    }

}
