package com.germarna.patterns.observer.hexagonalddd.application.notification.observer;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SlackNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.domain.events.reservation.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedSlackObserver implements Observer {
	private final SlackNotificationSender slackNotificationSender;

	@Override
	public void update(com.germarna.patterns.observer.hexagonalddd.domain.shared.events.DomainEvent event) {
		if (event instanceof ReservationCreatedEvent reservationCreatedEvent) {
			this.slackNotificationSender.sendNotification(reservationCreatedEvent.getReservationId(),
					reservationCreatedEvent.getGuestName(), reservationCreatedEvent.getRoomType());
		}
	}

	@Override
	public boolean isSubscribedTo(Class<?> eventType) {
		return ReservationCreatedEvent.class.equals(eventType);
	}
}
