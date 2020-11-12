package com.example.demo.repository.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends CrudRepository<Reservation, UUID> {

    List<Reservation> findAll();

    Reservation save(Reservation reservations);

    Optional<Reservation> findById(UUID id);

    void deleteById(UUID id);

    @Query("SELECT t FROM TableUnit t LEFT JOIN Reservation r ON t.id = r.tableId WHERE t.capacity = :capacity AND (r.endTime <= :dateTime OR r.id IS NULL)")
    List<TableUnit> getTablesByCapacityAndDate(@Param("capacity") int capacity, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE t.capacity = :capacity AND (r.endTime <= :dateTimeTwo OR r.id IS NULL) AND res.name = :name")
    List<TableUnit> getTablesByCapacityDateAndRestaurant(@Param("capacity") int capacity, @Param("dateTimeTwo") LocalDateTime dateTimeTwo,  @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId WHERE t.capacity = :capacity AND (r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL")
    List<TableUnit> getTablesByCapacityAndDatTimeRange(@Param("capacity") int capacity, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId WHERE (r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL")
    List<TableUnit> getTablesByDatTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


//    @Query("SELECT r FROM Restaurant r LEFT OUTER JOIN TableUnit t ON r.id=t.restaurantId LEFT OUTER JOIN Reservation reserv ON t.id = reserv.tableId WHERE ((reserv.endTime <= :endTime AND reserv.startTime>= :startTime) OR r.id IS NULL) AND t.capacity = :capacity AND r.name = :name")
//    List<Restaurant> getRestaurantByCapacityAndDateRange(@Param("endTime") LocalDateTime endTime, @Param("startTime") LocalDateTime startTime, @Param("capacity") int capacity, @Param("name") String name);




}
