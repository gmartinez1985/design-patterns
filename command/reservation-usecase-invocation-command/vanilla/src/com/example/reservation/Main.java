package com.example.reservation;

import com.example.reservation.service.cancelreservation.CancelReservationCommand;
import com.example.reservation.service.cancelreservation.CancelReservationService;
import com.example.reservation.service.cancelreservation.CancelReservationUseCase;
import com.example.reservation.service.createreservation.CreateReservationCommand;
import com.example.reservation.service.createreservation.CreateReservationService;
import com.example.reservation.service.createreservation.CreateReservationUseCase;
import com.example.reservation.shared.AsyncInvoker;
import com.example.reservation.shared.Command;
import com.example.reservation.shared.CommandHistory;
import com.example.reservation.shared.Invoker;
import com.example.reservation.utils.CommandsGenerator;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        // Receivers
        CreateReservationUseCase createReservationService = new CreateReservationService();
        CancelReservationUseCase cancelReservationService = new CancelReservationService();

        // Command content
        UUID roomId = UUID.randomUUID();
        UUID guestId = UUID.randomUUID();
        Date checkIn = new Date(); // now
        Date checkOut = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000); // +2 days

        // Creation with undo
        Command createReservationCommand = new CreateReservationCommand(
                createReservationService, cancelReservationService, roomId, guestId, checkIn, checkOut
        );
        Invoker invoker = new Invoker(createReservationCommand);
        invoker.invoke();
        invoker.undo();
        invoker.undo();

        // Manual undoing
        UUID randomResevationId = UUID.randomUUID();
        Command cancelReservationCommand = new CancelReservationCommand(cancelReservationService, randomResevationId);
        invoker.setCommand(cancelReservationCommand);
        invoker.invoke();

        // === BATCH EXECUTION ===
        final int AMOUNT_OF_COMMANDS = 10;
        var batch = CommandsGenerator.generateCommands(AMOUNT_OF_COMMANDS, createReservationService, cancelReservationService);
        var poolSize = Math.min(16, Runtime.getRuntime().availableProcessors() * 2);
        var async = new AsyncInvoker(poolSize);
        var ok = new AtomicInteger();
        var failed = new AtomicInteger();

        CompletableFuture.allOf(
                batch.stream()
                        .map(cmd -> async.setCommand(cmd).invokeAsync().whenComplete((v, ex) -> {
                            if (ex == null) ok.incrementAndGet();
                            else { failed.incrementAndGet(); System.err.println("[ASYNC-ERROR] " + ex.getMessage()); }
                        }))
                        .toArray(CompletableFuture[]::new)
        ).join();

        System.out.println("OK=" + ok.get() + " FAILED=" + failed.get());

        System.out.println("\n=== HISTORY ===");
        CommandHistory.getInstance()
                .snapshot()
                .forEach(System.out::println);
    }
}