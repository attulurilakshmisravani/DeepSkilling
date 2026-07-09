package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Exercise 2
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Exercise 5
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Exercise 6: throws instead of returning null, for exception-handling test
    public User getUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Exercise 7
    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

}
