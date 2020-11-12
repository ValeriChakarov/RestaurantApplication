package com.example.demo.services.user;

import com.example.demo.domain.User;
import com.example.demo.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private final List<User> users = new ArrayList<>();

    @BeforeEach
    public void setup(){
    user1 = new User();
        user1.setId(UUID.randomUUID());
        user1.setFirstName("Johny");
        user1.setLastName("Depp");
        user1.setEmail("johny.depp@gmail.com");
        user1.setPhoneNumber("+446634123986");

    user2 = new User();
        user2.setId(UUID.randomUUID());
        user2.setFirstName("Harrison");
        user2.setLastName("Ford");
        user2.setEmail("harrison.ford.com");
        user2.setPhoneNumber("+447732123456");

        users.add(user1);
        users.add(user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        Mockito.when(userRepository.findById(UUID.fromString("6b5bb95c-6b82-4cd0-8391-fb9a3599bd40")))
                .thenReturn(Optional.ofNullable(user1));

        Mockito.when(userRepository.findByName("Harrison"))
                .thenReturn(Optional.ofNullable(user2));

        Mockito.when(userRepository.save(user1)).thenReturn(user1);
    }

    @Test
    void getAllUsers() {
        List<User> foundStudents = userService.getAllUsers();

        assertNotNull(foundStudents);
        assertEquals(2, foundStudents.size());
    }

    @Test
    void getUserById() {
        User found = userService.getUserById(UUID.fromString("6b5bb95c-6b82-4cd0-8391-fb9a3599bd40"));

        assertNotNull(found);
        assertEquals(user1.getId(), found.getId());
        assertEquals(user1.getFirstName(), found.getFirstName());
        assertEquals(user1.getLastName(), found.getLastName());
        assertEquals(user1.getEmail(), found.getEmail());
        assertEquals(user1.getPhoneNumber(), found.getPhoneNumber());

    }

    @Test
    void getUserByName() {
        User found = userService.getUserByName("Harrison");

        assertNotNull(found);
        assertNotNull(found);
        assertEquals(user2.getId(), found.getId());
        assertEquals(user2.getFirstName(), found.getFirstName());
        assertEquals(user2.getLastName(), found.getLastName());
        assertEquals(user2.getEmail(), found.getEmail());
        assertEquals(user2.getPhoneNumber(), found.getPhoneNumber());
    }

    @Test
    void saveUser() {
        User found = userService.saveUser(user1);

        assertNotNull(found);
        assertEquals(user1.getFirstName(), found.getFirstName());
        assertEquals(user1.getId(), found.getId());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(user1.getId());

        Mockito.verify(userRepository, Mockito.times(1))
                .deleteById(user1.getId());

    }
}