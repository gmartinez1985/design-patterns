package com.germarna.patterns.observer.hexagonalddd.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Reservation domain model")
class ReservationTest {
	final String GUEST_NAME = "John Doe";
	final String GUEST_EMAIL = "johndoe@email.com";

	@Test
	@DisplayName("changeDates updates the dates when input is valid")
	void changeDatesUpdatesDatesWhenValid() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final UUID room = UUID.randomUUID();

		final Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.JANUARY, 10, 12, 0, 0);
		final Date in = cal.getTime();
		cal.set(2025, Calendar.JANUARY, 12, 12, 0, 0);
		final Date out = cal.getTime();

		final Reservation r = new Reservation(id, room, this.GUEST_NAME, this.GUEST_EMAIL, in, out);

		cal.set(2025, Calendar.JANUARY, 11, 12, 0, 0);
		final Date newIn = cal.getTime();
		cal.set(2025, Calendar.JANUARY, 13, 12, 0, 0);
		final Date newOut = cal.getTime();

		// WHEN
		r.changeDates(newIn, newOut);

		// THEN
		assertEquals(newIn, r.getCheckInDate());
		assertEquals(newOut, r.getCheckOutDate());
	}

	@Test
	@DisplayName("changeDates throws when any date is null (check-in null, check-out non-null)")
	void changeDatesThrowsWhenCheckInNull() {
		// GIVEN
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				new Date(), new Date());

		// WHEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> r.changeDates(null, new Date()));

		// THEN
		assertEquals("Check-in and check-out dates cannot be null", ex.getMessage());
	}

	@Test
	@DisplayName("changeDates throws when any date is null (check-in non-null, check-out null)")
	void changeDatesThrowsWhenCheckOutNull() {
		// GIVEN
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				new Date(), new Date());

		// WHEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> r.changeDates(new Date(), null));

		// THEN
		assertEquals("Check-in and check-out dates cannot be null", ex.getMessage());
	}

	@Test
	@DisplayName("changeDates throws when any date is null (both null)")
	void changeDatesThrowsWhenBothNull() {
		// GIVEN
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				new Date(), new Date());

		// WHEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> r.changeDates(null, null));

		// THEN
		assertEquals("Check-in and check-out dates cannot be null", ex.getMessage());
	}

	@Test
	@DisplayName("changeDates throws when check-in is not before check-out")
	void changeDatesThrowsWhenInNotBeforeOut() {
		// GIVEN
		final Date same = new Date();
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				same, same);

		// WHEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> r.changeDates(same, same));

		// THEN
		assertEquals("Check-in date must be before check-out date", ex.getMessage());
	}

	@Test
	@DisplayName("getRoomId returns the room id set in constructor")
	void getRoomIdReturnsValue() {
		// GIVEN
		final UUID room = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 86_400_000L); // +1 day
		final Reservation r = new Reservation(UUID.randomUUID(), room, this.GUEST_NAME, this.GUEST_EMAIL, checkIn,
				checkOut);

		// WHEN
		final UUID returned = r.getRoomId();

		// THEN
		assertEquals(room, returned);
	}

	@Test
	@DisplayName("getGuestName returns the guest name set in constructor")
	void getGuestNameReturnsValue() {
		// GIVEN
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 86_400_000L); // +1 ay
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				checkIn, checkOut);

		// WHEN
		final String returned = r.getGuestName();

		// THEN
		assertEquals(this.GUEST_NAME, returned);
	}

	@Test
	@DisplayName("getGuestEmail returns the guest email set in constructor")
	void getGuestEmailReturnsValue() {
		// GIVEN
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 86_400_000L); // +1 ay
		final Reservation r = new Reservation(UUID.randomUUID(), UUID.randomUUID(), this.GUEST_NAME, this.GUEST_EMAIL,
				checkIn, checkOut);

		// WHEN
		final String returned = r.getGuestEmail();

		// THEN
		assertEquals(this.GUEST_EMAIL, returned);
	}
}
