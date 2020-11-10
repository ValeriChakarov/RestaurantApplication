package com.example.demo.services.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("restaurantService")
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;


    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantsById(UUID id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("Restaurant name cannot be found."));
    }

    public Restaurant getRestaurantsByName(String name) {
        return restaurantRepository.findByName(name);
    }

    public List<Restaurant> getRestaurantAvailability() {
        return null;
    }

    public Restaurant addNewRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void removeRestaurant(UUID id) {
        restaurantRepository.deleteById(id);
    }
}
