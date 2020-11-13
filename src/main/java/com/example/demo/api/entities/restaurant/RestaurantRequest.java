package com.example.demo.api.entities.restaurant;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class RestaurantRequest {

    String name;

    String address;

    String phoneNumber;
}
