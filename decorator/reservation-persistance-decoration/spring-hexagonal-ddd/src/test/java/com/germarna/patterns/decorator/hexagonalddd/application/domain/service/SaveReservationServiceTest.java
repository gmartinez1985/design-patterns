package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveReservationServiceTest {

	@Mock
	private SaveReservationPort port;

	@InjectMocks
	private SaveReservationService service;

	@Test
	@DisplayName("createReservation persists and returns the generated id")
	void createReservationPersistsAndReturnsId() {
		// GIVEN
		final UUID generated = UUID.randomUUID();
		when(this.port.saveReservation(any())).thenReturn(generated);

		final UUID room = UUID.randomUUID();
		final UUID guest = UUID.randomUUID();
		final Date in = new Date(System.currentTimeMillis() + 86_400_000L);
		final Date out = new Date(System.currentTimeMillis() + 2 * 86_400_000L);

		// WHEN
		final UUID returned = this.service.createReservation(room, guest, in, out);

		// THEN
		final ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
		verify(this.port).saveReservation(captor.capture());
		assertNotNull(captor.getValue().getReservationId());
		assertEquals(generated, returned);
	}
}
