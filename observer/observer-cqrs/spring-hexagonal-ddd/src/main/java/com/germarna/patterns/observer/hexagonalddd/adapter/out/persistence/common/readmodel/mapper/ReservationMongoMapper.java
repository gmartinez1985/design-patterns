package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMongoMapper {
	ReservationView toDomain(ReservationMongoDocument document);
}
