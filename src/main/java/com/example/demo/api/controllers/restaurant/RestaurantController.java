package com.example.demo.api.controllers.restaurant;

import com.example.demo.api.entities.restaurant.AddNewRestaurantResponse;
import com.example.demo.api.entities.restaurant.RestaurantRequest;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.services.restaurant.RestaurantService;
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

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;

    @GetMapping("/allRestaurants")
    public ResponseEntity<List<Restaurant>> fetchAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping("/create")
//    public ResponseEntity<Restaurant> createRestaurant(@RequestParam String name, String address, String phoneNumber)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return ResponseEntity.ok(restaurantService.addNewRestaurant(restaurantRequest.getName(), restaurantRequest.getAddress(), restaurantRequest.getPhoneNumber()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> fetchRestaurantById(@PathVariable UUID id) {
        return ResponseEntity.ok(restaurantService.getRestaurantsById(id));
    }

    @GetMapping
    public ResponseEntity<Restaurant> fetchRestaurantByName(@RequestParam String name) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByName(name));
    }

    @DeleteMapping("/delete/{id}")
    public void removeRestaurant(@PathVariable UUID id) {
        restaurantService.removeRestaurantById(id);
    }

    //This api request is for testing purposes only. It allows you to create a dummy restaurant with random number of tables with different capacity.
    @PostMapping("/addNewRestaurantWithTables")
    public ResponseEntity<AddNewRestaurantResponse> createRestaurantWithTables(@RequestParam String name, @RequestParam String address, @RequestParam String phoneNumber) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(restaurantService.addNewRestaurantWithTables(name, address, phoneNumber));
    }

    @PostMapping("/addTable")
    public ResponseEntity<TableUnit> addTable(@RequestParam int capacity, @RequestParam String restaurantId) {
        return ResponseEntity.ok(restaurantService.addNewTable(capacity, UUID.fromString(restaurantId)));
    }

    @GetMapping("/availability/capacityName")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityRestaurantName(@RequestParam int capacity, @RequestParam String restaurantName) {
        return ResponseEntity.ok(restaurantService.getTablesByCapacityAndRestaurant(capacity, restaurantName));
    }

    @GetMapping("/availability/timeRangeName")
    public ResponseEntity<List<TableUnit>> fetchTablesByTimeRangeRestaurantName(@RequestParam String startTime,
                                                                                @RequestParam String endTime, @RequestParam String restaurantName) {
        return ResponseEntity.ok(restaurantService.getTablesByDateTimeRangeAndRestaurant(startTime, endTime, restaurantName));
    }

    @GetMapping("/availability/capacityTimeName")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityDateRestaurantName(@RequestParam int capacity, @RequestParam String dateTime, @RequestParam String restaurantName) {
        return ResponseEntity.ok(restaurantService.getTablesByCapacityDateTimeAndRestaurant(capacity, dateTime, restaurantName));
    }

    @GetMapping("/availability/capacityTimeRangeName")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityTimeRangeRestaurantName(@RequestParam int capacity, @RequestParam String startTime,
                                                                                        @RequestParam String endTime, @RequestParam String restaurantName) {
        return ResponseEntity.ok(restaurantService.getTablesByCapacityTimeRangeName(capacity, startTime, endTime, restaurantName));
    }
}
