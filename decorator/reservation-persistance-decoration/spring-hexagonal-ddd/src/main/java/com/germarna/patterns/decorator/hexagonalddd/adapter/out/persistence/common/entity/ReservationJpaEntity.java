package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.envers.Audited;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "RESERVATIONS")
@Data
@Audited
public class ReservationJpaEntity {
	@Id
	@Column(name = "RESERVATION_ID")
	private UUID reservationId;

	@Column(name = "ROOM_ID", nullable = false)
	private UUID roomId;

	@Column(name = "GUEST_ID", nullable = false)
	private UUID guestId;

	@Column(name = "CHECK_IN_DATE", nullable = false)
	private Date checkInDate;

	@Column(name = "CHECK_OUT_DATE", nullable = false)
	private Date checkOutDate;
}
