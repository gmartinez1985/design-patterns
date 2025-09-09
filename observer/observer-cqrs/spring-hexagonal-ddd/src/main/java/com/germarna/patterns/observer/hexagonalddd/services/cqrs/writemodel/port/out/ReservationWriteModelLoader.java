package com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.port.out;

import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.ReservationSnapshot;

import java.util.UUID;

public interface ReservationWriteModelLoader {
    ReservationSnapshot load(UUID reservationId);
}
