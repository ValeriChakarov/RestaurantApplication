package com.example.demo.services.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.reservation.ReservationRepository;
import com.example.demo.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service("reservationService")
@Transactional
public class ReservationService {


    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(UUID userId, LocalDateTime startTime, LocalDateTime endTime, int capacity, UUID restaurantID, UUID tableID) {
//        Reservation reservation = new Reservation(UUID.randomUUID(), userId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
//                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
//                restaurantID, tableID);
        Reservation reservation = new Reservation(UUID.randomUUID(), userId, startTime,
                endTime, capacity,
                restaurantID, tableID);
        return reservationRepository.save(reservation);
    }

    public void cancelReservationById(UUID id) {
        reservationRepository.deleteById(id);
    }

    public Reservation makeAReservationByCapacityAndDateTime(UUID userId, String startTime, String endTime, int capacity, String restaurantName) {
        TableUnit tableUnit = getTablesByCapacityDateTimeAndRestaurant(capacity, startTime,restaurantName).get(0);
        Reservation reservation = new Reservation(UUID.randomUUID(), userId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
                tableUnit.getRestaurantId(), tableUnit.getId());
        return reservationRepository.save(reservation);
    }

    public Reservation amendReservation(UUID id, int capacity, String endTime, String startTime, UUID tableId, UUID userId, UUID restaurantId) {
        Reservation reservation = new Reservation(id, userId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
                restaurantId, tableId);
        return reservationRepository.save(reservation);
    }

    public List<TableUnit> getTablesByCapacityDateTimeAndRestaurant(int capacity, String dateTime, String restaurantName) {
        return reservationRepository.getTablesByCapacityDateAndRestaurant(capacity,
                DateTimeFormatManager.getLocalDateTimeFormat(dateTime), restaurantName);
    }

    public List<TableUnit> getTablesByCapacityTimeRangeName(int capacity, String startTime, String endTime, String name){
        return reservationRepository.getTablesByCapacityDatTimeRangeAndRestaurant(capacity,DateTimeFormatManager.getLocalDateTimeFormat(endTime),
                                                                                    DateTimeFormatManager.getLocalDateTimeFormat(startTime),name);
    }

    public List<TableUnit> getTablesByDateTimeRangeAndRestaurant(String startTime, String endTime, String name){
        return reservationRepository.getTablesByDateTimeRangeAndRestaurant(DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                                                                            DateTimeFormatManager.getLocalDateTimeFormat(endTime),name);
    }

    public List<TableUnit> getTablesByCapacityAndRestaurant(int capacity, String name){
        return reservationRepository.getTablesByCapacityAndRestaurant(capacity,name);
    }
}
