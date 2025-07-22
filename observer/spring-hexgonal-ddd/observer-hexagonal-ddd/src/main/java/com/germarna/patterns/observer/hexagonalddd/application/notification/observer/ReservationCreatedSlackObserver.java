package com.germarna.patterns.observer.hexagonalddd.application.notification.observer;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SlackNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.domain.events.reservation.ReservationCreated;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedSlackObserver implements Observer {
	private final SlackNotificationSender slackNotificationSender;

	@Override
	public void update(com.germarna.patterns.observer.hexagonalddd.domain.shared.events.DomainEvent event) {
		if (event instanceof ReservationCreated reservationCreated) {
			this.slackNotificationSender.sendNotification(reservationCreated.getReservationId(),
					reservationCreated.getGuestName(), reservationCreated.getRoomType());
		}
	}

	@Override
	public boolean isSubscribedTo(Class<?> eventType) {
		return ReservationCreated.class.equals(eventType);
	}
}
