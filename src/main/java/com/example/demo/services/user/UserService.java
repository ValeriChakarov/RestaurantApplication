package com.example.demo.services.user;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.User;
import com.example.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("userService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User id cannot be found."));
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new RuntimeException("User name cannot be found."));
    }

    public User createUser(String firstName, String lastName, String phoneNumber, String email){
        User user = new User(UUID.randomUUID(), firstName,lastName,email,phoneNumber);
        return saveUser(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
