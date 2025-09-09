package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model.ReservationSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationSnapshotMapper {
	ReservationSnapshot toSnapshot(ReservationJpaEntity entity);
}
