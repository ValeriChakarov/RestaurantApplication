package com.example.demo.services.reservation;

import com.example.demo.api.entities.reservation.AmendRequestBody;
import com.example.demo.api.entities.reservation.ReservationRequest;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.TableUnit;
import com.example.demo.repository.reservation.ReservationRepository;
import com.example.demo.services.restaurant.RestaurantService;
import com.example.demo.utilities.DateTimeFormatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("reservationService")
@Transactional
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RestaurantService restaurantService;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = new Reservation(UUID.randomUUID(), UUID.fromString(reservationRequest.getGuestId()),
                DateTimeFormatManager.getLocalDateTimeFormat(reservationRequest.getStartTime()),
                DateTimeFormatManager.getLocalDateTimeFormat(reservationRequest.getEndTime()),
                reservationRequest.getCapacity(), UUID.fromString(reservationRequest.getRestaurantId()), UUID.fromString(reservationRequest.getTableId()));
        return reservationRepository.save(reservation);
    }

    public void cancelReservationById(UUID id) {
        reservationRepository.deleteById(id);
    }

    public Reservation makeAReservationByCapacityAndDateTime(UUID guestId, String startTime, String endTime, int capacity, String restaurantName) {
        List<TableUnit> availableTables = restaurantService.getTablesByCapacityDateTimeAndRestaurant(capacity, startTime, restaurantName);
        if (availableTables.isEmpty()) {
            throw new RuntimeException("No tables available.");
        }
        TableUnit tableUnit = availableTables.get(0);
        Reservation reservation = new Reservation(UUID.randomUUID(), guestId, DateTimeFormatManager.getLocalDateTimeFormat(startTime),
                DateTimeFormatManager.getLocalDateTimeFormat(endTime), capacity,
                tableUnit.getRestaurantId(), tableUnit.getId());
        return reservationRepository.save(reservation);
    }

    public void amendReservation(AmendRequestBody amendRequestBody) {
        reservationRepository.amendReservation(UUID.fromString(amendRequestBody.getId()), amendRequestBody.getCapacity(),
                DateTimeFormatManager.getLocalDateTimeFormat(amendRequestBody.getStartTime()),
                DateTimeFormatManager.getLocalDateTimeFormat(amendRequestBody.getEndTime()),
                UUID.fromString(amendRequestBody.getGuestId()), UUID.fromString(amendRequestBody.getRestaurantId()), UUID.fromString(amendRequestBody.getTableId()));
    }

}
