package com.example.demo.services.table;

import com.example.demo.domain.TableUnit;
import com.example.demo.repository.table.TableUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("tableUnitService")
public class TableUnitService {


    @Autowired
    TableUnitRepository tableUnitRepository;

    public List<TableUnit> getAllTables() {
        return tableUnitRepository.findAll();
    }

    public Optional<TableUnit> getTAbleById(UUID id) {
        return tableUnitRepository.findById(id);
    }

    public TableUnit addNewTable(TableUnit tableUnit) {
        tableUnitRepository.save(tableUnit);
        return tableUnit;
    }

    public void removeTable(UUID id) {
        tableUnitRepository.deleteById(id);
    }

}
