package com.germarna.patterns.observer.hexagonalddd.integration;

import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedEmailObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSlackObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSmsObserver;
import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.EmailNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SlackNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SmsNotificationSender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CreateReservationIT {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@MockitoSpyBean
	private ReservationCreatedEmailObserver reservationCreatedEmailObserver;
	@MockitoSpyBean
	private ReservationCreatedSlackObserver reservationCreatedSlackObserver;
	@MockitoSpyBean
	private ReservationCreatedSmsObserver reservationCreatedSmsObserver;

	@MockitoSpyBean
	private EmailNotificationSender emailNotificationSender;
	@MockitoSpyBean
	private SlackNotificationSender slackNotificationSender;
	@MockitoSpyBean
	private SmsNotificationSender smsNotificationSender;

	// Prevents this from running at app startup
	@MockitoBean
	private CommandLineRunner startupRunner;

	@Test
	@DisplayName("When creating a reservation, notifications are sent and observers receive the correct event")
	void shouldSendNotificationsAndNotifyObserversWhenReservationIsCreated() {
		// --- GIVEN ---
		final String guestName = "Jane Doe";
		final String roomType = "Suite";

		// --- WHEN ---
		final UUID reservationId = this.createReservationUseCase.createReservation(guestName, roomType);

		// --- THEN ---
		assertNotNull(reservationId, "Reservation ID should not be null");

		// Verify notifications were sent
		verify(this.emailNotificationSender, times(1)).sendNotification(reservationId, guestName, roomType);
		verify(this.slackNotificationSender, times(1)).sendNotification(reservationId, guestName, roomType);
		verify(this.smsNotificationSender, times(1)).sendNotification(reservationId, guestName, roomType);

		// Verify the observers received the correct event
		this.verifyObserver(this.reservationCreatedEmailObserver, reservationId, guestName, roomType);
		this.verifyObserver(this.reservationCreatedSlackObserver, reservationId, guestName, roomType);
		this.verifyObserver(this.reservationCreatedSmsObserver, reservationId, guestName, roomType);
	}

	private void verifyObserver(Object observer, UUID reservationId, String guestName, String roomType) {
		final ArgumentCaptor<ReservationCreatedEvent> captor = ArgumentCaptor.forClass(ReservationCreatedEvent.class);
		if (observer instanceof ReservationCreatedEmailObserver) {
			verify((ReservationCreatedEmailObserver) observer, times(1)).update(captor.capture());
		} else if (observer instanceof ReservationCreatedSlackObserver) {
			verify((ReservationCreatedSlackObserver) observer, times(1)).update(captor.capture());
		} else if (observer instanceof ReservationCreatedSmsObserver) {
			verify((ReservationCreatedSmsObserver) observer, times(1)).update(captor.capture());
		}
		final ReservationCreatedEvent event = captor.getValue();
		assertEquals(reservationId, event.getReservationId());
		assertEquals(guestName, event.getGuestName());
		assertEquals(roomType, event.getRoomType());
	}
}
