package com.example.mediator.reservation.service.cancelreservation;

import com.example.mediator.reservation.shared.Mediator;

import java.util.UUID;

public class CancelReservationService implements CancelReservationUseCase {
    private Mediator mediator;
    private UUID reservationId;

    @Override
    public void handle(UUID reservationId) {
        this.reservationId = reservationId;
        System.out.println("↩️  Reservation CANCELLED: " + reservationId);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String toString() {
        return "{reservationId=" + reservationId + "}";
    }
}
