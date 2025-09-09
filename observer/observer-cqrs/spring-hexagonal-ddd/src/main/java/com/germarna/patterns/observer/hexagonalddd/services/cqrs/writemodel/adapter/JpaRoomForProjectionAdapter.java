package com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.adapter;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.mapper.RoomSnapshotMapper;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.RoomSnapshot;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.port.out.RoomWriteModelLoader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaRoomForProjectionAdapter implements RoomWriteModelLoader {
    private final RoomJpaRepository roomJpaRepository;
    private final RoomSnapshotMapper roomSnapshotMapper;

    @Override
    @Transactional(readOnly = true)
    public RoomSnapshot load(UUID roomId) {
        final RoomJpaEntity found = this.roomJpaRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomId));
        return roomSnapshotMapper.toSnapshot(found);
    }
}
