package com.ufsc.controle.acesso.vaga.model;

import com.ufsc.controle.acesso.vaga.model.dto.ParkingSpotDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String parkingName;
    private Integer maximumCapacity;
    private Integer currentCapacity;

    public ParkingSpot(ParkingSpotDTO parkingSpotDTO) {
        updateFromDTO(parkingSpotDTO);
    }

    public void updateFromDTO(ParkingSpotDTO parkingSpotDTO) {
        parkingName = parkingSpotDTO.parkingName();
        maximumCapacity = parkingSpotDTO.maximumCapacity();
        currentCapacity = parkingSpotDTO.currentCapacity();
    }
}
