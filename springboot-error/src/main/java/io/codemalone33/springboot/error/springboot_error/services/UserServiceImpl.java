package io.codemalone33.springboot.error.springboot_error.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.codemalone33.springboot.error.springboot_error.models.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private List<User> users;
    
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
