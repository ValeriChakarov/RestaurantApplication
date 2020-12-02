package com.example.demo.api.entities.restaurant;//package com.example.demo.api.controllers.restaurant.responseEntity;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.HashMap;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class AddNewRestaurantResponse {

    String name;
    String address;
    String phoneNumber;
    HashMap<Integer, UUID> tableUnits;
}
