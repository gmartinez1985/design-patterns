package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SmsNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;
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
