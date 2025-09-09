package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ROOMS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomJpaEntity {

	@Id
	@Column(name = "ROOM_ID")
	private UUID id;

	@Column(name = "NUMBER", nullable = false)
	private String number;

	@Column(name = "ROOM_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private RoomType type;
}
