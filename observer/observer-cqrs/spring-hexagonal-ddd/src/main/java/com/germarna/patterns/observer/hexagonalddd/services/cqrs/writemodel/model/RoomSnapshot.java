package com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model;

import java.util.UUID;

public record RoomSnapshot(
        UUID id,
        String number,
        String type
) {}
