package com.example.reservation.service.createreservation;

import com.example.reservation.service.cancelreservation.CancelReservationUseCase;
import com.example.reservation.shared.Command;

import java.util.Date;
import java.util.UUID;

public class CreateReservationCommand implements Command {

    private final CreateReservationUseCase createReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final UUID roomId;
    private final UUID guestId;
    private final Date checkIn;
    private final Date checkOut;

    private UUID createdReservationId;

    public CreateReservationCommand(CreateReservationUseCase createReservationUseCase,
                                    CancelReservationUseCase cancelReservationUseCase,
                                    UUID roomId,
                                    UUID guestId,
                                    Date checkIn,
                                    Date checkOut) {
        this.createReservationUseCase = createReservationUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.roomId = roomId;
        this.guestId = guestId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public void execute() {
        createdReservationId = createReservationUseCase.handle(roomId, guestId, checkIn, checkOut);
    }

    @Override
    public void undo() {
        if (createdReservationId == null) {
            System.out.println("⚠️ Nothing to undo.");
            return;
        }
        cancelReservationUseCase.handle(createdReservationId);
        createdReservationId = null; // to avoid double undo
    }

    @Override
    public String toString() {
        return "{" +
                "roomId=" + roomId +
                ", guestId=" + guestId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", createdReservationId=" + createdReservationId +
                '}';
    }
}