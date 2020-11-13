package com.example.demo.api.restaurant;

import com.example.demo.api.restaurant.responseEntity.AddNewRestaurantResponse;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.services.reservation.ReservationService;
import com.example.demo.services.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final ReservationService reservationService;

    @GetMapping("/allRestaurants")
    public ResponseEntity<List<Restaurant>> fetchAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestParam String name, String address, String phoneNumber) {
        return ResponseEntity.ok(restaurantService.addNewRestaurant(name, address, phoneNumber));
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
    public void createRestaurant(@PathVariable UUID id) {
        restaurantService.removeRestaurantById(id);
    }

    @PostMapping("/addNewRestaurantWithTables")
    public ResponseEntity<AddNewRestaurantResponse> createRestaurantWithTables(@RequestParam String name, @RequestParam String address, @RequestParam String phoneNumber) {
        return ResponseEntity.ok(restaurantService.addNewRestaurantWithTables(name, address, phoneNumber));
    }

    @PostMapping("/addTable")
    public ResponseEntity<TableUnit> addTable(@RequestParam int capacity, @RequestParam UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.addNewTable(capacity, restaurantId));
    }

    @GetMapping("/tablesByCapacityTimeRestaurant")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityDateAndRestaurant(@RequestParam int capacity,@RequestParam String dateTime, @RequestParam String name) {
        return ResponseEntity.ok(reservationService.getTablesByCapacityDateTimeAndRestaurant(capacity, dateTime, name));
    }

    @GetMapping("/tablesByCapacityTimeRangeRestaurant")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityTimeRangeRestaurantName(@RequestParam int capacity,@RequestParam String startTime,
                                                                                        @RequestParam String endTime, @RequestParam String name) {
        return ResponseEntity.ok(reservationService.getTablesByCapacityTimeRangeName(capacity,startTime,endTime,name));
    }

    @GetMapping("/tablesByTimeRangeRestaurant")
    public ResponseEntity<List<TableUnit>> fetchTablesByTimeRangeRestaurantName(@RequestParam String startTime,
                                                                                        @RequestParam String endTime, @RequestParam String name) {
        return ResponseEntity.ok(reservationService.getTablesByDateTimeRangeAndRestaurant(startTime,endTime,name));
    }

    @GetMapping("/tablesByCapacityRestaurant")
    public ResponseEntity<List<TableUnit>> fetchTablesByCapacityAndRestaurantName(@RequestParam int capacity, @RequestParam String restaurantName) {
        return ResponseEntity.ok(reservationService.getTablesByCapacityAndRestaurant(capacity,restaurantName));
    }
}
