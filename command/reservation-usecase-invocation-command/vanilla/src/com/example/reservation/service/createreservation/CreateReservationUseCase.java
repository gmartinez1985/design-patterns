package com.example.reservation.service.createreservation;

import java.util.Date;
import java.util.UUID;

public interface CreateReservationUseCase {
    UUID handle(UUID roomId, UUID guestId, Date checkIn, Date checkOut);
}
