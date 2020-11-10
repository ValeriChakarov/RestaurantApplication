package com.example.demo.exceptions;


public class RestaurantNameNotFoundException extends Exception {
    private final String message;

    public RestaurantNameNotFoundException(String message) {
        this.message = message;
        System.out.println(message);
    }


    public void showExceptionMessage() {
        System.out.println("Restaurant name not found.");
    }
}
