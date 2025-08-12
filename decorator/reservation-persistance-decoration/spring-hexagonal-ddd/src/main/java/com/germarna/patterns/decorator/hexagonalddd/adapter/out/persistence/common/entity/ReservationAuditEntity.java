package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "RESERVATIONS_CUSTOM_AUDIT")
@Data
@Builder
public class ReservationAuditEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private UUID reservationId;
	private Date checkInDate;
	private Date checkOutDate;
	private UUID roomId;
	private UUID guestId;
	private LocalDateTime auditTimestamp;
	@Enumerated(EnumType.STRING)
	private ReservationActionType actionType;
}
