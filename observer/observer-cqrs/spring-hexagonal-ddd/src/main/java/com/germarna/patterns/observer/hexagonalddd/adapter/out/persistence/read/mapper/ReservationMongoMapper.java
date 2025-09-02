package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.events.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document.ReservationDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.RoomJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMongoMapper {
	@Mapping(target = "reservationId", source = "reservation.reservationId")
	@Mapping(target = "guestName", source = "reservation.guestName")
	@Mapping(target = "guestEmail", source = "reservation.guestEmail")
	@Mapping(target = "room", source = "room")
	ReservationDocument toDocument(ReservationJpaEntity reservation, RoomJpaEntity room,
			ReservationPersistedEvent event);

	ReservationDocument.RoomSnapshot toRoomSnapshot(RoomJpaEntity room);
}
