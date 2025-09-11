package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationSnapshot (
        UUID reservationId,
        UUID roomId,
        String guestName,
        String guestEmail,
        LocalDate checkInDate,
        LocalDate checkOutDate
) {}
