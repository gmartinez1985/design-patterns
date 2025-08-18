package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SmsNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedSmsObserver implements Observer<ReservationCreatedEvent> {
	private final SmsNotificationSender smsNotificationSender;

	@Override
	public void update(ReservationCreatedEvent event) {
		this.smsNotificationSender.sendNotification(event.getReservationId(), event.getGuestName(),
				event.getRoomType());
	}
}
