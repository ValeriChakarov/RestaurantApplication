package com.example.demo.api.user;

import com.example.demo.domain.User;
import com.example.demo.services.user.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void fetchAllUsers() throws Exception {
        User user = new User(UUID.fromString("3924ce45-d86e-41b3-857a-4312b2abb46"), "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        List<User> users = List.of(user);
        Mockito.doReturn(users).when(userService).getAllUsers();

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/user/allUsers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is(user.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(user.getPhoneNumber())));
    }

    @Test
    void createUser() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        Mockito.doReturn(user).when(userService).createUser("Sean", "Connery", "+447723123456", "sean.connery@gmail.com");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", "Sean");
        params.add("lastName", "Connery");
        params.add("email", "+447723123456");
        params.add("phoneNumber", "sean.connery@gmail.com");
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/user/create")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(user.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(user.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(user.getPhoneNumber())));
    }

    @Test
    void fetchUserById() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        Mockito.doReturn(user).when(userService).getUserById(id);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(user.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(user.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(user.getPhoneNumber())));
    }

    @Test
    void fetchUserByName() throws Exception {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", "Sean");
        Mockito.doReturn(user).when(userService).getUserByName("Sean");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(user.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(user.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(user.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(user.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(user.getPhoneNumber())));
    }
}