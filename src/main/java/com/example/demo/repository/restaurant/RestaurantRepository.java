package com.example.demo.repository.restaurant;

import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, UUID> {


    List<Restaurant> findAll();

    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(UUID id);

    Optional<Restaurant> findByName(String name);

    void deleteById(UUID id);

    @Query("SELECT r FROM Restaurant r LEFT OUTER JOIN TableUnit t ON t.capacity = :capacity")
    List<Restaurant> getRestaurantsByCapacity(int capacity);

    @Query("SELECT t FROM TableUnit t WHERE t.capacity = :capacity")
    List<TableUnit> getTablesByCapacity(int capacity);

    @Query("SELECT r FROM Restaurant r LEFT OUTER JOIN TableUnit t ON t.capacity = ?1")
    List<TableUnit> getTablesByCapacityAndName(int capacity, String name);







}
