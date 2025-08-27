package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindReservationServiceTest {

	@Mock
	private FindReservationPort port;

	@InjectMocks
	private FindReservationService service;

	@Test
	@DisplayName("findReservation delegates to port")
	void findReservationDelegatesToPort() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final Reservation expected = new Reservation(id, UUID.randomUUID(), UUID.randomUUID(), new Date(), new Date());
		when(this.port.loadReservation(id)).thenReturn(expected);

		// WHEN
		final Reservation result = this.service.findReservation(id);

		// THEN
		assertEquals(expected, result);
		verify(this.port).loadReservation(id);
	}
}
