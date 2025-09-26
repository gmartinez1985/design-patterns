package com.example.reservation.service.cancelreservation;

import java.util.UUID;

public interface CancelReservationUseCase {
    public void handle(UUID reservationId);
}
