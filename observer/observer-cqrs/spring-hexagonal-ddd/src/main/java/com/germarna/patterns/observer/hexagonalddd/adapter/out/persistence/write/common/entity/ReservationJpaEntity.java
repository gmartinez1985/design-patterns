package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
