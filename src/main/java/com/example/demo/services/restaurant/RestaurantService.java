package com.example.demo.services.restaurant;

import com.example.demo.api.restaurant.responseEntity.AddNewRestaurantResponse;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.restaurant.RestaurantRepository;
import com.example.demo.repository.table.TableUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service("restaurantService")
@Transactional
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    TableUnitRepository tableUnitRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant addNewRestaurant(String name, String address, String phoneNumber) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName(name);
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setAddress(address);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantsById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant id cannot be found."));
    }

    public Optional<Restaurant> getRestaurantsByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public void removeRestaurantById(UUID id) {
        restaurantRepository.deleteById(id);
    }

    public TableUnit addNewTable(int capacity, UUID restaurantId) {
        TableUnit tableUnit = new TableUnit();
        tableUnit.setId(UUID.randomUUID());
        tableUnit.setCapacity(capacity);
        tableUnit.setRestaurantId(restaurantId);
        return tableUnitRepository.save(tableUnit);
    }

    public AddNewRestaurantResponse addNewRestaurantWithTables(String name, String address, String phoneNumber) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName(name);
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setAddress(address);
        restaurantRepository.save(restaurant);
        Random rn = new Random();
        HashMap<Integer, UUID> tableMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            TableUnit tableEntity = new TableUnit();
            tableEntity.setId(UUID.randomUUID());
            tableEntity.setCapacity(rn.nextInt(10) + 1);
            tableEntity.setRestaurantId(restaurant.getId());
            tableMap.put(rn.nextInt(10) + 1, tableEntity.getId());
            tableUnitRepository.save(tableEntity);
        }
        return new AddNewRestaurantResponse(name, address, phoneNumber, tableMap);
    }
}
