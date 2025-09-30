package com.example.mediator.reservation.service.createreservation;

import com.example.mediator.reservation.shared.UseCase;

import java.util.Date;
import java.util.UUID;

public interface CreateReservationUseCase extends UseCase {
    UUID handle(UUID roomId, UUID guestId, Date checkIn, Date checkOut);
}
