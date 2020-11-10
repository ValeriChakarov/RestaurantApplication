package com.example.demo.api.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.services.restaurant.RestaurantService;
import com.example.demo.services.table.TableUnitService;
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
@RequestMapping("/api/v1/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    @Autowired
    private final RestaurantService restaurantService;

    @Autowired
    private final TableUnitService tableUnitService;

    @GetMapping("/allRestaurants")
    public ResponseEntity<List<Restaurant>> fetchAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant newRestaurant) {
        return ResponseEntity.ok(restaurantService.addNewRestaurant(newRestaurant));
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
        restaurantService.removeRestaurant(id);
    }

    @PostMapping("/addTable")
    public TableUnit createTable(@RequestBody TableUnit newTableUnit) {
        tableUnitService.addNewTable(newTableUnit);
        return newTableUnit;
    }

}
