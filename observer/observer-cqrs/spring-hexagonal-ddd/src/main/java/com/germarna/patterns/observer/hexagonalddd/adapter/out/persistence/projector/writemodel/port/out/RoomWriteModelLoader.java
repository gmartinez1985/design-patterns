package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.port.out;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model.RoomSnapshot;

import java.util.UUID;

public interface RoomWriteModelLoader {
    RoomSnapshot load(UUID roomId);
}
