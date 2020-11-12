package com.example.demo.services.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.reservation.ReservationRepository;
import com.example.demo.repository.restaurant.RestaurantRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("reservationService")
@Transactional
public class ReservationService {

    public static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(UUID userId, String startTime, String endTime, int capacity, UUID restaurantID, UUID tableID) {
        Reservation reservation = new Reservation();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        reservation.setId(UUID.randomUUID());
        reservation.setUserId(userId);
        reservation.setStartTime(LocalDateTime.parse(startTime, formatter));
        reservation.setEndTime(LocalDateTime.parse(endTime, formatter));
        reservation.setCapacity(capacity);
        reservation.setRestaurantId(restaurantID);
        reservation.setTableId(tableID);
        return reservationRepository.save(reservation);
    }

    public void cancelReservationById(UUID id) {
        reservationRepository.deleteById(id);
    }

    public Reservation makeAReservationByCapacityAndDateTime(UUID userId, String startTime, String endTime, int capacity) {
        Reservation reservation = new Reservation();
        TableUnit tableUnit = getTablesByCapacityAndDate(capacity, startTime).get(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        reservation.setId(UUID.randomUUID());
        reservation.setUserId(userId);
        reservation.setStartTime(LocalDateTime.parse(startTime, formatter));
        reservation.setEndTime(LocalDateTime.parse(endTime, formatter));
        reservation.setCapacity(capacity);
        reservation.setRestaurantId(tableUnit.getRestaurantId());
        reservation.setTableId(tableUnit.getId());
        return reservationRepository.save(reservation);
    }

    public Reservation amendReservation(UUID id, int capacity, String endTime, String startTime, UUID tableId, UUID userId, UUID restaurantId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        Reservation reservation = new Reservation(id,userId, LocalDateTime.parse(startTime,formatter),LocalDateTime.parse(endTime,formatter),capacity,restaurantId,tableId);
        return reservationRepository.save(reservation);
    }

    public List<TableUnit> getTablesByCapacityDateTimeAndRestaurant(int capacity, String dateTime,String restaurantMame) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return reservationRepository.getTablesByCapacityDateAndRestaurant(capacity, LocalDateTime.parse(dateTime, formatter),restaurantMame);
    }

    public List<TableUnit> getTablesByCapacityAndDate(int capacity, String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return reservationRepository.getTablesByCapacityAndDate(capacity, LocalDateTime.parse(dateTime, formatter));
    }

    public List<TableUnit> getTablesAndRestaurantsByCapacity(int capacity) {
        return restaurantRepository.getTablesByCapacity(capacity);
    }

}
