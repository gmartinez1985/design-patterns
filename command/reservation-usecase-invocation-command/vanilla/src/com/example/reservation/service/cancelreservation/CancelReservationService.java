package com.example.reservation.service.cancelreservation;

import java.util.UUID;

public class CancelReservationService implements CancelReservationUseCase {
    private UUID reservationId;

    @Override
    public void handle(UUID reservationId) {
        this.reservationId = reservationId;
        System.out.println("↩️  Reservation CANCELLED: " + reservationId);
    }

    @Override
    public String toString() {
        return "{" +
                "reservationId=" + reservationId +
                "}";
    }
}
