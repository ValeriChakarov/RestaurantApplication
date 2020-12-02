package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tableunits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableUnit {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "capacity", nullable = false)
    int capacity;

    @Column(name = "restaurant_id", nullable = false)
    @Type(type = "uuid-char")
    UUID restaurantId;
}
