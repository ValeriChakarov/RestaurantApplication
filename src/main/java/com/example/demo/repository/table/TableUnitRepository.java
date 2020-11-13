package com.example.demo.repository.table;

import com.example.demo.domain.TableUnit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TableUnitRepository extends CrudRepository<TableUnit, UUID> {

    Optional<TableUnit> findById(UUID id);

    List<TableUnit> findAll();

    void deleteById(UUID id);

    TableUnit save(TableUnit tableUnit);

}
