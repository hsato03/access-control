package com.ufsc.access.control.parking.model;

import com.ufsc.access.control.parking.model.dto.ParkingSpotDTO;
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
    private String parkingName;
    private Integer maximumCapacity;
    private Integer currentCapacity;

    public Parking(ParkingSpotDTO parkingSpotDTO) {
        updateFromDTO(parkingSpotDTO);
    }

    public void updateFromDTO(ParkingSpotDTO parkingSpotDTO) {
        parkingName = parkingSpotDTO.parkingName();
        maximumCapacity = parkingSpotDTO.maximumCapacity();
        currentCapacity = parkingSpotDTO.currentCapacity();
    }
}
