package com.example.mediator.reservation;

import com.example.mediator.reservation.service.cancelreservation.CancelReservationService;
import com.example.mediator.reservation.service.cancelreservation.CancelReservationUseCase;
import com.example.mediator.reservation.service.createreservation.CreateReservationService;
import com.example.mediator.reservation.service.createreservation.CreateReservationUseCase;
import com.example.mediator.reservation.shared.Mediator;
import com.example.mediator.reservation.shared.MediatorImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        CreateReservationUseCase createReservationUseCase = new CreateReservationService();
        CancelReservationUseCase cancelReservationUseCase = new CancelReservationService();
        Mediator mediator = new MediatorImpl();
        mediator.registerUseCase(createReservationUseCase);
        mediator.registerUseCase(cancelReservationUseCase);

        UUID roomId = UUID.randomUUID();
        UUID guestId = UUID.randomUUID();
        Date checkIn = daysFromNow(2);
        Date checkOut = daysFromNow(5);

        UUID id = mediator.<UUID>notify(createReservationUseCase, roomId, guestId, checkIn, checkOut);
        mediator.<Void>notify(cancelReservationUseCase, id);
    }

    private static Date daysFromNow(int days) {
        var cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
}
