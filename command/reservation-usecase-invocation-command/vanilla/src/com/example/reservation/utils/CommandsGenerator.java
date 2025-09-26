package com.example.reservation.utils;

import com.example.reservation.service.cancelreservation.CancelReservationCommand;
import com.example.reservation.service.cancelreservation.CancelReservationUseCase;
import com.example.reservation.service.createreservation.CreateReservationCommand;
import com.example.reservation.service.createreservation.CreateReservationUseCase;
import com.example.reservation.shared.Command;

import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.stream.IntStream;

public class CommandsGenerator {
    public static List<Command> generateCommands(
            int total,
            CreateReservationUseCase createUC,
            CancelReservationUseCase cancelUC
    ) {
        return IntStream.range(0, total)
                .mapToObj(i -> RND.nextDouble() < 0.7
                        ? createCreateReservation(createUC, cancelUC)
                        : createCancelReservation(cancelUC)
                )
                .toList();
    }
    private static final SplittableRandom RND = new SplittableRandom();

    private static Command createCreateReservation(
            CreateReservationUseCase createUC,
            CancelReservationUseCase cancelUC
    ) {
        UUID roomId = UUID.randomUUID();
        UUID guestId = UUID.randomUUID();
        Date checkIn = new Date();
        int days = 1 + RND.nextInt(5);
        Date checkOut = new Date(System.currentTimeMillis() + days * 24L * 60 * 60 * 1000);

        return new CreateReservationCommand(createUC, cancelUC, roomId, guestId, checkIn, checkOut);
    }

    private static Command createCancelReservation(CancelReservationUseCase cancelUC) {
        return new CancelReservationCommand(cancelUC, UUID.randomUUID());
    }
}
