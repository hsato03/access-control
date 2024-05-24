package com.ufsc.access.control.access.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AccessEntryResponse(UUID id, UUID userId, UUID parkingId, LocalDateTime entryDate) {
}
