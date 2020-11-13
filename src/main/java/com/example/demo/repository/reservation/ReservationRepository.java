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

//    @Query("SELECT t FROM TableUnit t LEFT JOIN Reservation r ON t.id = r.tableId WHERE t.capacity = :capacity AND (r.endTime <= :dateTime OR r.id IS NULL)")
//    List<TableUnit> getTablesByCapacityAndDate(@Param("capacity") int capacity, @Param("dateTime") LocalDateTime dateTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE t.capacity = :capacity AND (r.endTime <= :endTime OR r.id IS NULL) AND res.name = :name")
    List<TableUnit> getTablesByCapacityDateAndRestaurant(@Param("capacity") int capacity, @Param("endTime") LocalDateTime dateTimeTwo,  @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE t.capacity = :capacity AND ((r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL) AND res.name = :name ")
    List<TableUnit> getTablesByCapacityDatTimeRangeAndRestaurant(@Param("capacity") int capacity, @Param("endTime") LocalDateTime endTime, @Param("startTime") LocalDateTime startTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Reservation r ON t.id = r.tableId JOIN Restaurant res ON t.restaurantId = res.id WHERE ((r.endTime <= :endTime AND r.startTime>= :startTime) OR r.id IS NULL) AND res.name = :name")
    List<TableUnit> getTablesByDateTimeRangeAndRestaurant(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("name") String name);

    @Query("SELECT t FROM TableUnit t LEFT OUTER JOIN Restaurant r ON t.restaurantId = r.id WHERE t.capacity = :capacity AND r.name = :name")
    List<TableUnit> getTablesByCapacityAndRestaurant(@Param("capacity") int capacity, @Param("name") String name);
}
