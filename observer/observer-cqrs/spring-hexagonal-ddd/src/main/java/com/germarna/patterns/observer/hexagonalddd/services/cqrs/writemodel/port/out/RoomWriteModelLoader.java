package com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.port.out;

import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.RoomSnapshot;

import java.util.UUID;

public interface RoomWriteModelLoader {
    RoomSnapshot load(UUID roomId);
}
