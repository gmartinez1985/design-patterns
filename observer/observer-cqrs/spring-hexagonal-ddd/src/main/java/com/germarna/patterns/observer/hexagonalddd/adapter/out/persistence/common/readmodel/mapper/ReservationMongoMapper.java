package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.datamodel.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.model.ReservationProjectionView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMongoMapper {
	// @Mapping(target = "room", source = "room")
	ReservationMongoDocument toDocument(ReservationProjectionView view);

	ReservationMongoDocument.RoomSnapshot toRoomSnapshot(ReservationProjectionView.RoomView room);
}
