package com.example.demo.api.reservation;

import com.example.demo.repository.reservation.ReservationRepository;
import com.example.demo.services.reservation.ReservationService;
import com.example.demo.services.restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    @Autowired
    private final ReservationService reservationService;
}
