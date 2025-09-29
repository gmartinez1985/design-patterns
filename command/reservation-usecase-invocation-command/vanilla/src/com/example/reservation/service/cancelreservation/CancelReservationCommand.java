package com.example.reservation.service.cancelreservation;

import com.example.reservation.shared.Command;

import java.util.UUID;

public class CancelReservationCommand implements Command {
    private final CancelReservationUseCase cancelReservationUseCase;
    private final UUID reservationId;

    public CancelReservationCommand(CancelReservationUseCase cancelReservationUseCase, UUID reservationId) {
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.reservationId = reservationId;
    }

    @Override
    public void execute() {
        cancelReservationUseCase.handle(reservationId);
    }

    @Override
    public String toString() {
        return "CancelReservationCommand{" +
                "reservationId=" + reservationId +
                '}';
    }
}
