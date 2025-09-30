package com.example.mediator.reservation.shared;

import com.example.mediator.reservation.service.cancelreservation.CancelReservationUseCase;
import com.example.mediator.reservation.service.createreservation.CreateReservationUseCase;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MediatorImpl implements Mediator {
    private CreateReservationUseCase createReservationUseCase;
    private CancelReservationUseCase cancelReservationUseCase;


    @Override
    public void registerUseCase(UseCase useCase) {
        if (useCase instanceof CreateReservationUseCase cr) {
            this.createReservationUseCase = cr;
            cr.setMediator(this);
        } else if (useCase instanceof CancelReservationUseCase cc) {
            this.cancelReservationUseCase = cc;
            cc.setMediator(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R notify(UseCase sender, Object... args) {
        if(sender instanceof CreateReservationUseCase) {
            return (R) onCreate(args);
        } else if(sender instanceof CancelReservationUseCase) {
            return (R) onCancel(args);
        } else {
            throw new IllegalArgumentException("Use Case not supported: " + sender.getClass().getName());
        }
    }

    private UUID onCreate(Object... args){
        requireArgs(args, 4);
        Objects.requireNonNull(createReservationUseCase, "CreateReservationUseCase no registrado");
        UUID roomId  = (UUID) args[0];
        UUID guestId = (UUID) args[1];
        Date checkIn = (Date) args[2];
        Date checkOut= (Date) args[3];
        return createReservationUseCase.handle(roomId, guestId, checkIn, checkOut);
    }

    private Void onCancel(Object... args){
        requireArgs(args, 1);
        Objects.requireNonNull(cancelReservationUseCase, "CancelReservationUseCase no registrado");
        UUID reservationId = (UUID) args[0];
        cancelReservationUseCase.handle(reservationId);
        return null;
    }

    private static void requireArgs(Object[] args, int n) {
        if (args == null || args.length < n)
            throw new IllegalArgumentException("It was expected " + n + " arguments, it has been received: " + (args == null ? 0 : args.length));
    }
}
