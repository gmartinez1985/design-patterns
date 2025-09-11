package com.germarna.patterns.observer.hexagonalddd.application.domain.response;

import java.util.Date;
import java.util.UUID;

public record ReservationView(
        UUID reservationId,
        String guestName,
        String guestEmail,
        Date checkInDate,
        Date checkOutDate,
        RoomView room
) {
    public record RoomView(UUID id, String type, String number) {}
}
