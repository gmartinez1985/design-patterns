package com.example.reservation.service.createreservation;

import com.example.reservation.model.Reservation;

import java.util.Date;
import java.util.UUID;

public class CreateReservationService implements CreateReservationUseCase {

    @Override
    public UUID handle(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
        final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestId, checkIn, checkOut);
        System.out.println("\t✅ Reservation created with ID: " + reservation.getReservationId());
        return reservation.getReservationId();
    }
}
