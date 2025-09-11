package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model;

import java.util.UUID;

public record RoomSnapshot(
        UUID id,
        String number,
        String type
) {}
