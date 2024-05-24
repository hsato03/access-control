package com.ufsc.access.control.access.model;

import com.ufsc.access.control.access.model.dto.AccessDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_access")
@Getter
@Setter
@NoArgsConstructor
public class Access {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private UUID parkingId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime entryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime exitDate;

    public Access(AccessDTO access) {
        userId = access.userId();
        parkingId = access.parkingId();
    }

    @PrePersist
    public void onPersist() {
        entryDate = LocalDateTime.now();
    }
}
