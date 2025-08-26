package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SlackNotificationSender;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedSlackObserver implements Observer<ReservationCreatedEvent> {
	private final SlackNotificationSender slackNotificationSender;

	@Override
	public void update(ReservationCreatedEvent event) {
		this.slackNotificationSender.sendNotification(event.reservationId(), event.guestName(), event.roomType());
	}
}
