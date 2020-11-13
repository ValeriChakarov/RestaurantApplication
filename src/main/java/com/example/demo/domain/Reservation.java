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
    UUID id;

    @Column(name = "guest_id", nullable = false)
    @Type(type = "uuid-char")
    UUID guestId;

    @Column(name = "start_time", nullable = false)
    LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    LocalDateTime endTime;

    @Column(name = "capacity", nullable = false)
    int capacity;

    @Column(name = "restaurant_id", nullable = false)
    @Type(type = "uuid-char")
    UUID restaurantId;

    @Column(name = "table_id", nullable = false)
    @Type(type = "uuid-char")
    UUID tableId;

}
