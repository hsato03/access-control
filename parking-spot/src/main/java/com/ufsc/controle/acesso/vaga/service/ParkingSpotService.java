package com.ufsc.controle.acesso.vaga.service;

import com.ufsc.controle.acesso.vaga.model.ParkingSpot;
import com.ufsc.controle.acesso.vaga.model.dto.ParkingSpotDTO;
import com.ufsc.controle.acesso.vaga.repository.ParkingSpotRepository;
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
    public Page<ParkingSpot> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Transactional(readOnly = true)
    public ParkingSpot findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with UUID %s not found.", id)));
    }

    @Transactional
    public ParkingSpot save(ParkingSpotDTO parkingSpot) {
        ParkingSpot parkingSpotToSave = new ParkingSpot(parkingSpot);
        return repository.save(parkingSpotToSave);
    }

    @Transactional
    public ParkingSpot update(UUID id, ParkingSpotDTO parkingSpot) {
        ParkingSpot parkingSpotToUpdate = findById(id);
        parkingSpotToUpdate.updateFromDTO(parkingSpot);
        return repository.save(parkingSpotToUpdate);
    }

    public void delete(UUID id) {
        ParkingSpot parkingSpot = findById(id);
        repository.delete(parkingSpot);
    }
}
