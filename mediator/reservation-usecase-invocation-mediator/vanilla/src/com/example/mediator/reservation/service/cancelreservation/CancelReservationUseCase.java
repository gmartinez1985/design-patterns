package com.example.mediator.reservation.service.cancelreservation;

import com.example.mediator.reservation.shared.UseCase;

import java.util.UUID;

public interface CancelReservationUseCase extends UseCase {
    void handle(UUID reservationId);
}
