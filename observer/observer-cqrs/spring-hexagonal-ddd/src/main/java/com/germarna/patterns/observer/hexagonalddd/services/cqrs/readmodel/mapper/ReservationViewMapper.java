package com.germarna.patterns.observer.hexagonalddd.services.cqrs.readmodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.ReservationProjectionView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationViewMapper {
	ReservationMongoDocument toDocument(ReservationProjectionView view);

	ReservationMongoDocument.RoomSnapshot toRoomSnapshot(ReservationProjectionView.RoomView room);
}
