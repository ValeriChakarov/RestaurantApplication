package com.example.demo.services.guest;

import com.example.demo.api.entities.guest.GuestRequest;
import com.example.demo.domain.Guest;
import com.example.demo.repository.guest.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service("guestService")
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public List<Guest> getAllUsers() {
        return guestRepository.findAll();
    }

    public Guest getUserById(UUID id) {
        return guestRepository.findById(id).orElseThrow(() -> new RuntimeException("User id cannot be found."));
    }

    public Guest getUserByName(String name) {
        return guestRepository.findByName(name).orElseThrow(() -> new RuntimeException("User name cannot be found."));
    }

    public Guest createUser(GuestRequest guestRequest) {
        Guest guest = new Guest(UUID.randomUUID(), guestRequest.getFirstName(), guestRequest.getLastName(), guestRequest.getEmail(), guestRequest.getPhoneNumber());
        return saveUser(guest);
    }

    public Guest saveUser(Guest guest) {
        return guestRepository.save(guest);
    }

    public void deleteUser(UUID id) {
        guestRepository.deleteById(id);
    }
}
