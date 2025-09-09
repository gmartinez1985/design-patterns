package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationProjectionMapper {
	@Mapping(target = "reservationId", source = "reservation.reservationId")
	@Mapping(target = "guestName", source = "reservation.guestName")
	@Mapping(target = "guestEmail", source = "reservation.guestEmail")
	@Mapping(target = "room", source = "room")
	@Mapping(target = "timestamp", source = "event.timestamp")
	ReservationMongoDocument toMongoDocument(ReservationJpaEntity reservation, RoomJpaEntity room, ReservationPersistedEvent event);

	@Mapping(target = "type", expression = "java(room.getType().name())") // enum → String
	ReservationMongoDocument.RoomSnapshot toMongoDocument(Room room);
}
