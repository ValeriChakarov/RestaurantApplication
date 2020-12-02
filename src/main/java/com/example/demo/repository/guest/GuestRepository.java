package com.example.demo.repository.guest;

import com.example.demo.domain.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends CrudRepository<Guest, UUID> {

    Optional<Guest> findById(UUID id);

    List<Guest> findAll();

    void deleteById(UUID id);

    Guest save(Guest guest);

    void delete(Guest guest);

    @Query("SELECT guest FROM Guest guest WHERE guest.firstName = :name")
    Optional<Guest> findByName(String name);

}
