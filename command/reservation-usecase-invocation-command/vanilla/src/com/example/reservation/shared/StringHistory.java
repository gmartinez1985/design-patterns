package com.example.reservation.shared;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class StringHistory {
    private static final StringHistory INSTANCE = new StringHistory();
    private final CopyOnWriteArrayList<String> lines = new CopyOnWriteArrayList<>();

    private StringHistory() { }

    public static StringHistory getInstance() { return INSTANCE; }

    public void add(String line) { lines.add(line); }

    public List<String> snapshot() { return List.copyOf(lines); }

    public void clear() { lines.clear(); }
}
