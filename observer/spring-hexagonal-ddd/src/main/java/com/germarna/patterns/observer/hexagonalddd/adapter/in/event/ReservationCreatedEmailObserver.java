package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.EmailNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedEmailObserver implements Observer<ReservationCreatedEvent> {
	private final EmailNotificationSender emailNotificationSender;

	@Override
	public void update(ReservationCreatedEvent event) {
		this.emailNotificationSender.sendNotification(event.reservationId(), event.guestName(), event.roomType());
	}
}
