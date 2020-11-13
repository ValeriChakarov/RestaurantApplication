package com.example.demo.api.entities.reservation;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class ReservationRequestBody {

    String guestId;

    String startTime;

    String endTime;

    Integer capacity;

    String restaurantId;

    String tableId;
}
