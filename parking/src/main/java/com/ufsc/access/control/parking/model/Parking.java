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
    private Integer capacity;

    public Parking(ParkingDTO parkingDTO) {
        updateFromDTO(parkingDTO);
    }

    public void updateFromDTO(ParkingDTO parkingSpotDTO) {
        if (parkingSpotDTO.name() != null) {
            name = parkingSpotDTO.name();
        }

        if (parkingSpotDTO.capacity() != null) {
            capacity = parkingSpotDTO.capacity();
        }
    }
}
