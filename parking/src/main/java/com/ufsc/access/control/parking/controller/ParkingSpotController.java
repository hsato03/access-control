package com.ufsc.access.control.parking.controller;

import com.ufsc.access.control.parking.model.Parking;
import com.ufsc.access.control.parking.model.dto.ParkingSpotDTO;
import com.ufsc.access.control.parking.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    @Autowired
    ParkingSpotService service;

    @GetMapping
    public Page<Parking> findAll(@PageableDefault(size = 20, sort = {"parkingName"}) Pageable page) {
        return service.findAll(page);
    }

    @GetMapping("/{id}")
    public Parking findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Parking> save(@RequestBody ParkingSpotDTO parkingSpot, UriComponentsBuilder uriBuilder) {
        Parking savedParkingSpot = service.save(parkingSpot);
        URI uri = uriBuilder.path("/parking-spot/{id}").buildAndExpand(savedParkingSpot.getId()).toUri();
        return ResponseEntity.created(uri).body(savedParkingSpot);
    }

    @PutMapping("/{id}")
    public Parking update(@PathVariable UUID id, @RequestBody ParkingSpotDTO parkingSpot) {
        return service.update(id, parkingSpot);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
