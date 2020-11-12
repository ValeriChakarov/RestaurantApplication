package com.example.demo.api.restaurant.responseEntity;

import lombok.RequiredArgsConstructor;
import lombok.Value;


public class RestaurantTablesResponse {

    private String name;

    private int count;

    public RestaurantTablesResponse() {
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
