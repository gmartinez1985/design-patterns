package com.germarna.patterns.observer.hexagonalddd.application.port.out.room;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;

import java.util.UUID;

public interface FindRoomPort {
	Room loadRoom(UUID roomId);
}
