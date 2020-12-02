package com.example.demo.api.controllers.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.services.restaurant.RestaurantService;
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

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void fetchAllRestaurants() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id, "HeddonStreetKitchen", "Old Stret", "+447723123456");
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
    @WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
    void createRestaurant() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id, "OldSChoolKitchen", "Wimbledon Street", "+447732123456");
        Mockito.doReturn(restaurant).when(restaurantService).addNewRestaurant("OldSChoolKitchen", "Wimbledon Street", "+447732123456");
        String addRestaurantRequest = " {\"name\": \"OldSChoolKitchen\",\"address\": \"Wimbledon Street\",\"phoneNumber\": \"+447732123456\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/restaurant/create")
                .contentType(MediaType.APPLICATION_JSON).content(addRestaurantRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(restaurant.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(restaurant.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is(restaurant.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(restaurant.getPhoneNumber())));
    }

    @Test
    void fetchRestaurantById() throws Exception {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = new Restaurant(id, "HeddonStreetKitchen", "Old Stret", "+447723123456");
        Mockito.doReturn(restaurant).when(restaurantService).getRestaurantsById(id);

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/" + id)
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
        Restaurant restaurant = new Restaurant(id, name, "Old Stret", "+447723123456");
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
    void fetchTablesByCapacityRestaurantName() throws Exception {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        TableUnit tableUnit = new TableUnit(id, 7, restaurantId);
        List<TableUnit> tables = List.of(tableUnit);
        Mockito.doReturn(tables).when(restaurantService).getTablesByCapacityAndRestaurant(7, "TestKitchen");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("capacity", "7");
        params.add("restaurantName", "TestKitchen");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/availability/capacityName")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(tableUnit.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(tableUnit.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(tableUnit.getRestaurantId().toString())));
    }

    @Test
    void fetchTablesByTimeRangeRestaurantName() throws Exception {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        TableUnit tableUnit = new TableUnit(id, 7, restaurantId);
        List<TableUnit> tables = List.of(tableUnit);
        Mockito.doReturn(tables).when(restaurantService).getTablesByDateTimeRangeAndRestaurant("2020-11-13 : 14:00:00", "2020-11-13 : 16:00:00", "TestKitchen");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("startTime", "2020-11-13 : 14:00:00");
        params.add("endTime", "2020-11-13 : 16:00:00");
        params.add("restaurantName", "TestKitchen");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/availability/timeRangeName")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(tableUnit.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(tableUnit.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(tableUnit.getRestaurantId().toString())));
    }

    @Test
    void fetchTablesByCapacityDateRestaurantName() throws Exception {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        TableUnit tableUnit = new TableUnit(id, 7, restaurantId);
        List<TableUnit> tables = List.of(tableUnit);
        Mockito.doReturn(tables).when(restaurantService).getTablesByCapacityDateTimeAndRestaurant(7, "2020-11-13 17:00:00", "TestKitchen");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("capacity", "7");
        params.add("dateTime", "2020-11-13 17:00:00");
        params.add("restaurantName", "TestKitchen");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/availability/capacityTimeName")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(tableUnit.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(tableUnit.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(tableUnit.getRestaurantId().toString())));
    }

    @Test
    void fetchTablesByCapacityTimeRangeRestaurantName() throws Exception {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        TableUnit tableUnit = new TableUnit(id, 7, restaurantId);
        List<TableUnit> tables = List.of(tableUnit);
        Mockito.doReturn(tables).when(restaurantService).getTablesByCapacityTimeRangeName(7, "2020-11-13 : 14:00:00", "2020-11-13 : 16:00:00", "TestKitchen");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("capacity", "7");
        params.add("startTime", "2020-11-13 : 14:00:00");
        params.add("endTime", "2020-11-13 : 16:00:00");
        params.add("restaurantName", "TestKitchen");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurant/availability/capacityTimeRangeName")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(tableUnit.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(tableUnit.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(tableUnit.getRestaurantId().toString())));

    }
}