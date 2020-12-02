package com.example.demo.api.controllers.reservation;

import com.example.demo.api.entities.reservation.AmendRequestBody;
import com.example.demo.api.entities.reservation.ReservationRequest;
import com.example.demo.domain.Reservation;
import com.example.demo.services.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

    @GetMapping("/allReservations")
    public ResponseEntity<List<Reservation>> fetchAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(reservationService.createReservation(reservationRequest));
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable UUID id) {
        reservationService.cancelReservationById(id);
    }

    @PostMapping("/createWithPreferences")
    public ResponseEntity<Reservation> makeAReservationByCapacityAndDateRange(@RequestParam UUID guestID, @RequestParam String startTime,
                                                                              @RequestParam String endTime, @RequestParam int capacity, @RequestParam String restaurantName) {
        return ResponseEntity.ok(reservationService.makeAReservationByCapacityAndDateTime(guestID, startTime, endTime, capacity, restaurantName));
    }

    @PutMapping("/amend")
    public void amendReservation(@RequestBody AmendRequestBody amendRequestBody) {
        reservationService.amendReservation(amendRequestBody);
    }
}
