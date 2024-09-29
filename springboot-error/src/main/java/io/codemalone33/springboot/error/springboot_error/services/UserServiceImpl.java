package io.codemalone33.springboot.error.springboot_error.services;

import java.util.List;

import io.codemalone33.springboot.error.springboot_error.models.User;

public class UserServiceImpl implements UserService {

    private List<User> users;

    public UserServiceImpl() {
        this.users = List.of(
            new User(1L, "John", "Gonzalez"),
            new User(2L, "Jane", "Ramirez"),
            new User(3L, "Joe", "Santos"),
            new User(4L, "Sandra", "Lopez"),
            new User(5L, "Mario", "Gonzalez"));
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
        return user;
    }

}
