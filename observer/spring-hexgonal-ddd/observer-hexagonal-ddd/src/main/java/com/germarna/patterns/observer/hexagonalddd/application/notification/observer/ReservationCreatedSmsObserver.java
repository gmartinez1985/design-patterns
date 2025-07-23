package com.germarna.patterns.observer.hexagonalddd.application.notification.observer;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SmsNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.domain.events.reservation.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.DomainEvent;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedSmsObserver implements Observer {
	private final SmsNotificationSender smsNotificationSender;

	@Override
	public void update(DomainEvent event) {
		if (event instanceof ReservationCreatedEvent reservationCreatedEvent) {
			this.smsNotificationSender.sendNotification(reservationCreatedEvent.getReservationId(),
					reservationCreatedEvent.getGuestName(), reservationCreatedEvent.getRoomType());
		}
	}

	@Override
	public boolean isSubscribedTo(Class<?> eventType) {
		return ReservationCreatedEvent.class.equals(eventType);
	}
}
