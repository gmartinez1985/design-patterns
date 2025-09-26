package com.example.reservation.shared;

import java.util.Collection;
import java.util.concurrent.*;

public class AsyncInvoker implements AutoCloseable {
    private final ExecutorService executor;
    private final boolean managedExecutor;
    private final StringHistory history;

    public AsyncInvoker(int poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
        this.managedExecutor = true;
        this.history = StringHistory.getInstance();
    }

    public CompletableFuture<Void> invokeAsync(Command command) {
        return CompletableFuture.runAsync(() -> runWithLogging("AsyncCommand", command, command::execute), executor);
    }
    public CompletableFuture<Void> undoAsync(Command command) {
        return CompletableFuture.runAsync(() -> runWithLogging("AsyncCommand(undo)", command, command::undo), executor);
    }
    public CompletableFuture<Void> invokeAllAsync(Collection<? extends Command> commands) {
        return CompletableFuture.allOf(commands.stream().map(this::invokeAsync).toArray(CompletableFuture[]::new));
    }

    private void runWithLogging(String label, Command command, Runnable action) {
        final String commandName = command.getClass().getSimpleName();
        final String commandId   = Integer.toHexString(System.identityHashCode(command));
        final long t0 = System.nanoTime();

        try {
            action.run();
            long ms = (System.nanoTime() - t0) / 1_000_000;
            printAndStore(formatLine(label, commandName, commandId, "SUCCESS", ms, command.toString(), null));
        } catch (RuntimeException e) {
            long ms = (System.nanoTime() - t0) / 1_000_000;
            printAndStore(formatLine(label, commandName, commandId, "ERROR", ms, command.toString(), e.toString()));
            throw e;
        }
    }

    private String formatLine(String label, String name, String id, String outcome, long durationMs, String content, String error) {
        StringBuilder sb = new StringBuilder()
                .append(label).append("={")
                .append("name=").append(name).append(", ")
                .append("id=").append(id).append(", ")
                .append("outcome=").append(outcome).append(", ")
                .append("durationMs=").append(durationMs).append(", ")
                .append("content=").append(content);
        if (error != null) sb.append(", error=").append(error);
        sb.append("}");
        return sb.toString();
    }

    private void printAndStore(String line) {
        System.out.println(line);
        if (history != null) history.add(line);
    }

    public void shutdown() { if (managedExecutor) executor.shutdown(); }
    @Override public void close() { shutdown(); }
}
