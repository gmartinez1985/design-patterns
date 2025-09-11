package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "RESERVATIONS")
@Data
public class ReservationJpaEntity {
	@Id
	@Column(name = "RESERVATION_ID")
	private UUID reservationId;

	@Column(name = "ROOM_ID", nullable = false)
	private UUID roomId;

	@Column(name = "GUEST_NAME", nullable = false)
	private String guestName;

	@Column(name = "GUEST_EMAIL", nullable = false)
	private String guestEmail;

	@Column(name = "CHECK_IN_DATE", nullable = false)
	private Date checkInDate;

	@Column(name = "CHECK_OUT_DATE", nullable = false)
	private Date checkOutDate;
}
