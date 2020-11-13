package com.example.demo.api.entities.guest;


import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class GuestRequest {

    String firstName;

    String lastName;

    String email;

    String phoneNumber;
}
