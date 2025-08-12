package com.germarna.patterns.decorator.hexagonalddd.application.port.in;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;

import java.util.Date;
import java.util.UUID;

public interface ChangeDatesOfReservationUseCase {
	Reservation changeDatesOfReservation(UUID reservationId, Date newStartDate, Date newEndDate);
}
