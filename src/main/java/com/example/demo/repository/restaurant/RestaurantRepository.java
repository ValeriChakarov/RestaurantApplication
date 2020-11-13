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

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE t.capacity = :capacity AND (r.endTime <= :endTime OR r.id IS NULL) AND res.name = :name")
    List<TableUnit> getTablesByCapacityDateAndRestaurant(@Param("capacity") int capacity, @Param("endTime") LocalDateTime endTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE t.capacity = :capacity AND ((r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL) AND res.name = :name ")
    List<TableUnit> getTablesByCapacityDatTimeRangeAndRestaurant(@Param("capacity") int capacity, @Param("endTime") LocalDateTime endTime, @Param("startTime") LocalDateTime startTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE ((r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL) AND res.name = :name")
    List<TableUnit> getTablesByDateTimeRangeAndRestaurant(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Restaurant r ON t.restaurantId = r.id WHERE t.capacity = :capacity AND r.name = :name")
    List<TableUnit> getTablesByCapacityAndRestaurant(@Param("capacity") int capacity, @Param("name") String name);
}
