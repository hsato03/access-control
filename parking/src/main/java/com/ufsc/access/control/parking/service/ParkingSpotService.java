package com.ufsc.access.control.parking.service;

import com.ufsc.access.control.parking.model.Parking;
import com.ufsc.access.control.parking.model.dto.ParkingSpotDTO;
import com.ufsc.access.control.parking.repository.ParkingSpotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ParkingSpotService {

    @Autowired
    ParkingSpotRepository repository;

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
    public Parking save(ParkingSpotDTO parkingSpot) {
        Parking parkingSpotToSave = new Parking(parkingSpot);
        return repository.save(parkingSpotToSave);
    }

    @Transactional
    public Parking update(UUID id, ParkingSpotDTO parkingSpot) {
        Parking parkingSpotToUpdate = findById(id);
        parkingSpotToUpdate.updateFromDTO(parkingSpot);
        return repository.save(parkingSpotToUpdate);
    }

    public void delete(UUID id) {
        Parking parkingSpot = findById(id);
        repository.delete(parkingSpot);
    }
}
