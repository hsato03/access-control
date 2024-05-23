package com.ufsc.access.control.parking.model;

import com.ufsc.access.control.parking.model.dto.ParkingDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_parking")
@Getter
@Setter
@NoArgsConstructor
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer maximumCapacity;
    private Integer currentCapacity;

    public Parking(ParkingDTO parkingDTO) {
        updateFromDTO(parkingDTO);
    }

    public void updateFromDTO(ParkingDTO parkingSpotDTO) {
        name = parkingSpotDTO.name();
        maximumCapacity = parkingSpotDTO.maximumCapacity();
        currentCapacity = parkingSpotDTO.currentCapacity();
    }
}
