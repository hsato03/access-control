package com.ufsc.access.control.parking.repository;

import com.ufsc.access.control.parking.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingRepository extends JpaRepository<Parking, UUID> {
}
