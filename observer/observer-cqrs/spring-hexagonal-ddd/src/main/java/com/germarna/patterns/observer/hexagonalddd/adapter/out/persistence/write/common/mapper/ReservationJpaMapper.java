package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationJpaMapper {
	Reservation toDomain(ReservationJpaEntity entity);
	ReservationJpaEntity toEntity(Reservation domain);
}
