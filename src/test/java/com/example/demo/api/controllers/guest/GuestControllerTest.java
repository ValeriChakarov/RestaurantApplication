package com.example.demo.api.controllers.guest;

import com.example.demo.api.entities.guest.GuestRequest;
import com.example.demo.domain.Guest;
import com.example.demo.services.guest.GuestService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@WebMvcTest(GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GuestService guestService;

    @Test
    void fetchAllUsers() throws Exception {
        Guest guest = new Guest(UUID.fromString("3924ce45-d86e-41b3-857a-4312b2abb46"), "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        List<Guest> guests = List.of(guest);
        Mockito.doReturn(guests).when(guestService).getAllUsers();

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/guest/allGuests")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is(guest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is(guest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(guest.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(guest.getPhoneNumber())));
    }

    @Test
    @WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
    void createUser() throws Exception {
        UUID id = UUID.randomUUID();
        Guest guest = new Guest(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        GuestRequest guestRequest = new GuestRequest("Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        Mockito.doReturn(guest).when(guestService).createUser(guestRequest);
        String json = "{\"firstName\":\"Sean\",\"lastName\":\"Connery\",\"email\":\"sean.connery@gmail.com\",\"phoneNumber\":\"+447723123456\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/guest/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(guest.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(guest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(guest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(guest.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(guest.getPhoneNumber())));
    }

    @Test
    void fetchUserById() throws Exception {
        UUID id = UUID.randomUUID();
        Guest guest = new Guest(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        Mockito.doReturn(guest).when(guestService).getUserById(id);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/guest/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(guest.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(guest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(guest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(guest.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(guest.getPhoneNumber())));
    }

    @Test
    void fetchUserByName() throws Exception {
        UUID id = UUID.randomUUID();
        Guest guest = new Guest(id, "Sean", "Connery", "sean.connery@gmail.com", "+447723123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", "Sean");
        Mockito.doReturn(guest).when(guestService).getUserByName("Sean");

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/guest")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(guest.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(guest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(guest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(guest.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(guest.getPhoneNumber())));
    }
}