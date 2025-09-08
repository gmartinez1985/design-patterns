package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.findroom;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.datamodel.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.mapper.RoomJpaMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.room.FindRoomPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FindRoomPersistenceAdapter implements FindRoomPort {
	private final RoomJpaRepository roomJpaRepository;

	private final RoomJpaMapper roomJpaMapper;

	@Override
	public Room loadRoom(UUID roomId) {
		final RoomJpaEntity found = this.roomJpaRepository.findById(roomId)
				.orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
		return this.roomJpaMapper.toDomain(found);
	}
}
