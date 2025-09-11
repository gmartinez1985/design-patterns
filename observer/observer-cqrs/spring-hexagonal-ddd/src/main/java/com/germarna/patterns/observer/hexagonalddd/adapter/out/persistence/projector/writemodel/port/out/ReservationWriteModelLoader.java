package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.port.out;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model.ReservationSnapshot;

import java.util.UUID;

public interface ReservationWriteModelLoader {
    ReservationSnapshot load(UUID reservationId);
}
