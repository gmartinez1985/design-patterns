package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.EmailNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedEmailObserver implements Observer {
	private final EmailNotificationSender emailNotificationSender;

	@Override
	public void update(DomainEvent event) {
		if (event instanceof ReservationCreatedEvent reservationCreatedEvent) {
			this.emailNotificationSender.sendNotification(reservationCreatedEvent.getReservationId(),
					reservationCreatedEvent.getGuestName(), reservationCreatedEvent.getRoomType());
		}
	}

	@Override
	public boolean isSubscribedTo(Class<?> eventType) {
		return ReservationCreatedEvent.class.equals(eventType);
	}
}
