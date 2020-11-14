package com.example.demo.services.restaurant;

import com.example.demo.api.entities.restaurant.AddNewRestaurantResponse;
import com.example.demo.api.entities.restaurant.RestaurantRequest;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.restaurant.RestaurantRepository;
import com.example.demo.repository.table.TableUnitRepository;
import com.example.demo.utilities.DateTimeFormatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
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

    public Restaurant addNewRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(), restaurantRequest.getName(), restaurantRequest.getPhoneNumber(), restaurantRequest.getAddress());
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantsById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant id cannot be found."));
    }

    public Restaurant getRestaurantsByName(String name) {
        return restaurantRepository.findByName(name).orElseThrow(() -> new RuntimeException("Restaurant name cannot be found."));
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

    public AddNewRestaurantResponse addNewRestaurantWithTables(String name, String address, String phoneNumber) throws NoSuchAlgorithmException {
        Random rand = SecureRandom.getInstanceStrong();
        Restaurant restaurant = new Restaurant();
        restaurant.setId(UUID.randomUUID());
        restaurant.setName(name);
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setAddress(address);
        restaurantRepository.save(restaurant);
        HashMap<Integer, UUID> tableMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            TableUnit tableEntity = new TableUnit();
            tableEntity.setId(UUID.randomUUID());
            tableEntity.setCapacity(rand.nextInt(10) + 1);
            tableEntity.setRestaurantId(restaurant.getId());
            tableMap.put(rand.nextInt(10) + 1, tableEntity.getId());
            tableUnitRepository.save(tableEntity);
        }
        return new AddNewRestaurantResponse(name, address, phoneNumber, tableMap);
    }

    public List<TableUnit> getTablesByCapacityDateTimeAndRestaurant(int capacity, String dateTime, String restaurantName) {
        return restaurantRepository.getTablesByCapacityDateAndRestaurant(capacity,
                DateTimeFormatManager.getLocalDateTimeFormat(dateTime), restaurantName);
    }

    public List<TableUnit> getTablesByCapacityTimeRangeName(int capacity, String startTime, String endTime, String name) {
        return restaurantRepository.getTablesByCapacityDatTimeRangeAndRestaurant(capacity, DateTimeFormatManager.getLocalDateTimeFormat(endTime),
                DateTimeFormatManager.getLocalDateTimeFormat(startTime), name);
    }

    public List<TableUnit> getTablesByDateTimeRangeAndRestaurant(String startTime, String endTime, String name) {
        return restaurantRepository.getTablesByDateTimeRangeAndRestaurant(DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), name);
    }

    public List<TableUnit> getTablesByCapacityAndRestaurant(int capacity, String name) {
        return restaurantRepository.getTablesByCapacityAndRestaurant(capacity, name);
    }
}
