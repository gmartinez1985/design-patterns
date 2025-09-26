package com.example.reservation.shared;

public interface Command {
    void execute();
    default void undo() {}
}
