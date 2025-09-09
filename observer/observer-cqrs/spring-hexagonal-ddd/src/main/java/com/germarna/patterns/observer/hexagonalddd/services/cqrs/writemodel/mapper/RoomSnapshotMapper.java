package com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.RoomSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomSnapshotMapper {

	RoomSnapshot toSnapshot(RoomJpaEntity entity);
}
