package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "user_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "restaurant_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID restaurantId;

    @Column(name = "table_id", nullable = false)
    @Type(type = "uuid-char")
    private UUID tableId;

}
