package com.example.demo.api.entities.reservation;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class AmendRequestBody {


    String id;

    String guestId;

    String startTime;

    String endTime;

    int capacity;

    String restaurantId;

    String tableId;

}
