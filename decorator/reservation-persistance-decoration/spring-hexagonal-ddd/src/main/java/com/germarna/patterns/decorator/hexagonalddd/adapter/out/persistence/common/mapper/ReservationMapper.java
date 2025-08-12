package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	Reservation toDomain(ReservationJpaEntity entity);
	ReservationJpaEntity toEntity(Reservation domain);
}
