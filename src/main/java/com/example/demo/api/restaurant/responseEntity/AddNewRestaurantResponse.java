package com.example.demo.api.restaurant.responseEntity;//package com.example.demo.api.restaurant.responseEntity;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.HashMap;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class AddNewRestaurantResponse {

    private final String name;
    private final String address;
    private final String phoneNumber;
    private final HashMap<Integer, UUID> tableUnits;
}
