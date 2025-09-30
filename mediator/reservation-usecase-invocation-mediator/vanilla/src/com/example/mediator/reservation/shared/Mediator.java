package com.example.mediator.reservation.shared;

public interface Mediator {
    void registerUseCase(UseCase useCase);
    <R> R notify(UseCase sender, Object... args);
}