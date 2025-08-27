package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeDatesOfReservationServiceTest {

	@Mock
	private FindReservationPort findReservationPort;

	@Mock
	private UpdateReservationPort updateReservationPort;

	@InjectMocks
	private ChangeDatesOfReservationService service;

	@Test
	@DisplayName("changeDatesOfReservation finds, updates and returns the updated reservation (happy path)")
	void changeDatesFindsUpdatesAndReturnsUpdated() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final Reservation existing = new Reservation(id, UUID.randomUUID(), UUID.randomUUID(), new Date(), new Date());
		when(this.findReservationPort.loadReservation(id)).thenReturn(existing);
		when(this.updateReservationPort.updateReservation(any())).thenAnswer(inv -> inv.getArgument(0));

		final Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.MARCH, 1);
		final Date newIn = cal.getTime();
		cal.set(2025, Calendar.MARCH, 5);
		final Date newOut = cal.getTime();

		// WHEN
		final Reservation updated = this.service.changeDatesOfReservation(id, newIn, newOut);

		// THEN
		assertEquals(newIn, updated.getCheckInDate());
		assertEquals(newOut, updated.getCheckOutDate());
		verify(this.findReservationPort).loadReservation(id);
		verify(this.updateReservationPort).updateReservation(existing);
	}

	@Test
	@DisplayName("throws when newStartDate is null (A || B → A=true, B=false)")
	void throwsWhenStartDateIsNull() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final Date end = new Date();

		// WHEN / THEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.service.changeDatesOfReservation(id, null, end));

		assertEquals("Start/End date can't be null", ex.getMessage());
		verifyNoInteractions(this.findReservationPort, this.updateReservationPort);
	}

	@Test
	@DisplayName("throws when newEndDate is null (A || B → A=false, B=true)")
	void throwsWhenEndDateIsNull() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final Date start = new Date();

		// WHEN / THEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.service.changeDatesOfReservation(id, start, null));

		assertEquals("Start/End date can't be null", ex.getMessage());
		verifyNoInteractions(this.findReservationPort, this.updateReservationPort);
	}

	@Test
	@DisplayName("throws when both dates are null (A || B → A=true, B=true)")
	void throwsWhenBothDatesAreNull() {
		// GIVEN
		final UUID id = UUID.randomUUID();

		// WHEN / THEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.service.changeDatesOfReservation(id, null, null));

		assertEquals("Start/End date can't be null", ex.getMessage());
		verifyNoInteractions(this.findReservationPort, this.updateReservationPort);
	}

	@Test
	@DisplayName("throws when start is not before end (negated before() branch)")
	void throwsWhenStartNotBeforeEnd() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final Date same = new Date();

		// WHEN / THEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.service.changeDatesOfReservation(id, same, same));

		assertEquals("Check-in date must be before check-out date", ex.getMessage());
		verifyNoInteractions(this.findReservationPort, this.updateReservationPort);
	}

	@Test
	@DisplayName("returns null and does not update when reservation not found (reservation == null)")
	void returnsNullWhenReservationNotFound() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		when(this.findReservationPort.loadReservation(id)).thenReturn(null);

		final Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.APRIL, 10);
		final Date start = cal.getTime();
		cal.set(2025, Calendar.APRIL, 12);
		final Date end = cal.getTime();

		// WHEN
		final Reservation result = this.service.changeDatesOfReservation(id, start, end);

		// THEN
		assertNull(result, "Service should return null when reservation is not found");
		verify(this.findReservationPort).loadReservation(id);
		verifyNoInteractions(this.updateReservationPort);
	}
}
