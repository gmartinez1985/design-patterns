package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.mapper;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.datamodel.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomJpaMapper {
	RoomJpaEntity toEntity(Room domain);
	Room toDomain(RoomJpaEntity entity);
}
