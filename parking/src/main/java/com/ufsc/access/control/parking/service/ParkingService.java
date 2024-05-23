package com.ufsc.access.control.parking.service;

import com.ufsc.access.control.parking.model.Parking;
import com.ufsc.access.control.parking.model.dto.ParkingDTO;
import com.ufsc.access.control.parking.repository.ParkingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ParkingService {

    @Autowired
    ParkingRepository repository;

    @Transactional(readOnly = true)
    public Page<Parking> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional(readOnly = true)
    public Parking findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with UUID %s not found.", id)));
    }

    @Transactional
    public Parking save(ParkingDTO parking) {
        Parking parkingSpotToSave = new Parking(parking);
        return repository.save(parkingSpotToSave);
    }

    @Transactional
    public Parking update(UUID id, ParkingDTO parking) {
        Parking parkingSpotToUpdate = findById(id);
        parkingSpotToUpdate.updateFromDTO(parking);
        return repository.save(parkingSpotToUpdate);
    }

    public void delete(UUID id) {
        Parking parkingSpot = findById(id);
        repository.delete(parkingSpot);
    }
}
