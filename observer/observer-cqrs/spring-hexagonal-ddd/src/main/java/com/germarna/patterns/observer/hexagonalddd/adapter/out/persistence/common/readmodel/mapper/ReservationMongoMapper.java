package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.ReservationProjectionView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMongoMapper {
	Reservation toDomain(ReservationMongoDocument document);
}
