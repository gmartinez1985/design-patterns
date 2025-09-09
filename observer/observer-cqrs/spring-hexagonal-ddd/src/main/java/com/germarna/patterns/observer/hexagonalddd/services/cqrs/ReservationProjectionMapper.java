package com.germarna.patterns.observer.hexagonalddd.services.cqrs;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.ReservationSnapshot;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.RoomSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationProjectionMapper {
	@Mapping(target = "reservationId", source = "reservation.reservationId")
	@Mapping(target = "guestName", source = "reservation.guestName")
	@Mapping(target = "guestEmail", source = "reservation.guestEmail")
	@Mapping(target = "room", source = "room")
	@Mapping(target = "timestamp", source = "event.timestamp")
	ReservationProjectionView toView(ReservationSnapshot reservation, RoomSnapshot room, ReservationPersistedEvent event);

	@Mapping(target = "type", expression = "java(room.getType().name())") // enum → String
	ReservationProjectionView.RoomView toView(Room room);
}
