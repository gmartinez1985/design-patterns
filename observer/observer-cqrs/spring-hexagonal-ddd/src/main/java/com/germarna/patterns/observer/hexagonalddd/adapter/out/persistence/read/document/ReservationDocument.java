package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Document(collection = "RESERVATIONS_PROJECTIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDocument {

	@Id
	private UUID reservationId;

	private RoomSnapshot room;

	private String guestName;

	private String guestEmail;

	private Date checkInDate;

	private Date checkOutDate;

	private Instant timestamp;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RoomSnapshot {
		private UUID id;
		private String type;
		private String number;
	}

}
