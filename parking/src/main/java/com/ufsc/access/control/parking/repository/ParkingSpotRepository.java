package com.ufsc.controle.acesso.vaga.repository;

import com.ufsc.controle.acesso.vaga.model.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {
}
