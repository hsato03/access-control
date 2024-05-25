package com.ufsc.access.control.access.repository;

import com.ufsc.access.control.access.model.Access;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccessRepository extends JpaRepository<Access, UUID> {
    Optional<Access> findByUserIdAndParkingIdAndExitDateNull(UUID userId, UUID parkingId);
}
