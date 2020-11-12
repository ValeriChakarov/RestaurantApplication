package com.example.demo.api.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.domain.User;
import com.example.demo.services.reservation.ReservationService;
import com.example.demo.services.restaurant.RestaurantService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private ReservationService reservationService;

    @Test
    void fetchAllRestaurants() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id,"HeddonStreetKitchen","Old Stret","+447723123456");
        List<Restaurant> restaurants = List.of(restaurant);
        Mockito.doReturn(restaurants).when(restaurantService).getAllRestaurants();

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/allRestaurants")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(restaurant.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(restaurant.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is(restaurant.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(restaurant.getPhoneNumber())));
    }

    @Test
    void createRestaurant() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id,"HeddonStreetKitchen","Old Street","+447723123456");
        Mockito.doReturn(restaurant).when(restaurantService).addNewRestaurant("HeddonStreetKitchen","Old Street","+447723123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "HeddonStreetKitchen");
        params.add("address", "Old Street");
        params.add("phoneNumber", "+447723123456");
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/restaurant/addRestaurant")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(restaurant.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(restaurant.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is(restaurant.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(restaurant.getPhoneNumber())));
    }

    @Test
    void fetchRestaurantById() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id,"HeddonStreetKitchen","Old Stret","+447723123456");
        Mockito.doReturn(restaurant).when(restaurantService).getRestaurantsById(id);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(restaurant.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(restaurant.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is(restaurant.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(restaurant.getPhoneNumber())));
    }

    @Test
    void fetchRestaurantByName() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "HeddonStreetKitchen";
        Restaurant restaurant = new Restaurant(id,name,"Old Stret","+447723123456");
        Mockito.doReturn(restaurant).when(restaurantService).getRestaurantsByName(name);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", name);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(restaurant.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(restaurant.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is(restaurant.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(restaurant.getPhoneNumber())));
    }

    @Test
    void addTable() {
    }

    @Test
    void fetchTablesBySeatingCapacityAndDateTimeRange() {

    }

    @Test
    void fetchTablesByCapacityAndDate() throws Exception {
    }

    @Test
    void fetchTablesByCapacityDateTimeRestaurantName() {
    }



    @Test
    void fetchTablesByDateTimeRange() {
    }
}