package com.example.reservation.shared;

public class Invoker {

    private Command command;
    private final StringHistory history; // puede ser null

    public Invoker(Command command) {
        this.command = command;
        history = StringHistory.getInstance();
    }

    public void setCommand(Command command) { this.command = command; }

    public void invoke() { runWithLogging("Command", () -> command.execute()); }
    public void undo()   { runWithLogging("Command(undo)", () -> command.undo()); }

    private void runWithLogging(String label, Runnable action) {
        final String commandName = command.getClass().getSimpleName();
        final String commandId = Integer.toHexString(System.identityHashCode(command));
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
}