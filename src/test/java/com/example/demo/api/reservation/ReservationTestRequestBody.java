package com.example.demo.api.reservation;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class ReservationTestRequestBody {

    private UUID id;

    private UUID userId;

    private String startTime;

    private String endTime;

    private int capacity;

    private UUID restaurantId;

    private UUID tableId;

}
