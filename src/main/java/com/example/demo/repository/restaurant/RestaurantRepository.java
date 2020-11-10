package com.example.demo.repository.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {

//    Restaurant findById(UUID id);

    @Query("SELECT restaurant FROM Restaurant restaurant WHERE restaurant.name = :name")
    Restaurant findByName(String name);

    List<Restaurant> findAll();

    void deleteById(UUID id);


}
