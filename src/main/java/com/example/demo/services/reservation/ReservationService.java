package com.example.demo.services.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.Restaurant;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.reservation.ReservationRepository;
import com.example.demo.repository.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Reservation makeAReservationByCapacityAndDateTime(UUID userId, String startTime, String endTime, int capacity) {
        TableUnit tableUnit = getTablesByCapacityAndDate(capacity, startTime).get(0);
        Reservation reservation = new Reservation(UUID.randomUUID(), userId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
                tableUnit.getRestaurantId(), tableUnit.getId());
        return reservationRepository.save(reservation);
    }

    public Reservation amendReservation(UUID id, int capacity, String endTime, String startTime, UUID tableId, UUID userId, UUID restaurantId) {
        Reservation reservation = new Reservation(UUID.randomUUID(), userId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
                restaurantId, tableId);
        return reservationRepository.save(reservation);
    }

    public List<TableUnit> getTablesByCapacityDateTimeAndRestaurant(int capacity, String dateTime, String restaurantMame) {
        return reservationRepository.getTablesByCapacityDateAndRestaurant(capacity,
                DateTimeFormatManager.getLocalDateTimeFormat(dateTime), restaurantMame);
    }

    public List<TableUnit> getTablesByCapacityAndDate(int capacity, String dateTime) {
        return reservationRepository.getTablesByCapacityAndDate(capacity,
                DateTimeFormatManager.getLocalDateTimeFormat(dateTime));
    }

    public List<TableUnit> getTablesAndRestaurantsByCapacity(int capacity) {
        return restaurantRepository.getTablesByCapacity(capacity);
    }

    public List<TableUnit> getTablesByCapacityAndDateRange(int capacity, String startDateTime, String endDateTime) {
        return reservationRepository.getTablesByCapacityAndDatTimeRange(capacity,
                DateTimeFormatManager.getLocalDateTimeFormat(startDateTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endDateTime));
    }

    public List<TableUnit> getTablesByDateRange(String startDateTime, String endDateTime) {
        return reservationRepository.getTablesByDatTimeRange(DateTimeFormatManager.getLocalDateTimeFormat(startDateTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endDateTime));
    }

//    public List<Restaurant> getRestaurantByCapacityAndDateRange(String startDateTime, String endDateTime, int capacity, String name){
//        return reservationRepository.getRestaurantByCapacityAndDateRange(DateTimeFormatManager.getLocalDateTimeFormat(startDateTime),
//                DateTimeFormatManager.getLocalDateTimeFormat(endDateTime), capacity, name);
//    }


}
