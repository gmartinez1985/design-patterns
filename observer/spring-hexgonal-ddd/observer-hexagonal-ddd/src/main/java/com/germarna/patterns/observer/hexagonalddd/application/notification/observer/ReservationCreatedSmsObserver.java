package com.germarna.patterns.observer.hexagonalddd.application.notification.observer;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SmsNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.domain.events.reservation.ReservationCreated;
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
		if (event instanceof ReservationCreated reservationCreated) {
			this.smsNotificationSender.sendNotification(reservationCreated.getReservationId(),
					reservationCreated.getGuestName(), reservationCreated.getRoomType());
		}
	}

	@Override
	public boolean isSubscribedTo(Class<?> eventType) {
		return ReservationCreated.class.equals(eventType);
	}
}
