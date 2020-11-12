package com.example.demo.api.reservation;

import com.example.demo.domain.Reservation;
import com.example.demo.domain.TableUnit;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> fetchAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> makeReservation(@RequestParam UUID userID,@RequestParam String startTime,
                                                         @RequestParam String endTime,@RequestParam int capacity,
                                                         @RequestParam UUID restaurantID,@RequestParam UUID tableID) {
        return ResponseEntity.ok(reservationService.createReservation(userID, startTime, endTime, capacity, restaurantID, tableID));
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelReservation(@PathVariable UUID id) {
        reservationService.cancelReservationById(id);
    }

    @PostMapping("/createWithPreferences")
    public ResponseEntity<Reservation> makeAReservationByCapacityAndDateTime(@RequestParam UUID userID, @RequestParam String startTime,
                                                                         @RequestParam String endTime, @RequestParam int capacity) {
        return ResponseEntity.ok(reservationService.makeAReservationByCapacityAndDateTime(userID, startTime, endTime, capacity));
    }


    @PutMapping("/amend")
    public ResponseEntity<Reservation> amendReservation(@RequestParam UUID id, @RequestParam int capacity,
                                                        @RequestParam String endTime, @RequestParam String startTime,
                                                        @RequestParam UUID tableId, @RequestParam UUID userId, @RequestParam UUID restaurantId){
            return ResponseEntity.ok(reservationService.amendReservation(id,capacity,endTime,startTime,tableId,userId,restaurantId));
    }
}
