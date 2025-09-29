package com.example.reservation.shared;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class CommandHistory {
    private static final CommandHistory INSTANCE = new CommandHistory();
    private final CopyOnWriteArrayList<String> lines = new CopyOnWriteArrayList<>();

    private CommandHistory() { }

    public static CommandHistory getInstance() { return INSTANCE; }

    public void add(String line) { lines.add(line); }

    public List<String> snapshot() { return List.copyOf(lines); }

    public void clear() { lines.clear(); }
}
