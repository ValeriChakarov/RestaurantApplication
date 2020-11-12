package com.example.demo.api.user;

import com.example.demo.domain.User;
import com.example.demo.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> fetchAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String phoneNumber){
        return ResponseEntity.ok(userService.createUser(firstName,lastName,email,phoneNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<User> fetchUserByName(@RequestParam String firstName) {
        return ResponseEntity.ok(userService.getUserByName(firstName));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable UUID id){
        userService.deleteUser(id);
    }
}
