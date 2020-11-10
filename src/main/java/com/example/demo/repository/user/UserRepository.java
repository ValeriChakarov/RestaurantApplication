package com.example.demo.repository.user;

import com.example.demo.domain.TableUnit;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findById(UUID id);

    @Query("SELECT user FROM User user WHERE user.firstName = :name")
    Optional<User> findByName(String name);

    List<User> findAll();

    void deleteById(UUID id);

    User save(User user);
}
