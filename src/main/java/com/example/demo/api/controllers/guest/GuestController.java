package com.example.demo.api.controllers.guest;

import com.example.demo.api.entities.guest.GuestRequest;
import com.example.demo.domain.Guest;
import com.example.demo.services.guest.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
public class GuestController {

    @Autowired
    private final GuestService guestService;

    @GetMapping("/allGuests")
    public ResponseEntity<List<Guest>> fetchAllGuests() {
        return ResponseEntity.ok(guestService.getAllUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<Guest> createGuest(@RequestBody GuestRequest guestRequest) {
        return ResponseEntity.ok(guestService.createUser(guestRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> fetchGuestById(@PathVariable UUID id) {
        return ResponseEntity.ok(guestService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<Guest> fetchUserByName(@RequestParam String firstName) {
        return ResponseEntity.ok(guestService.getUserByName(firstName));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable UUID id) {
        guestService.deleteUser(id);
    }
}
