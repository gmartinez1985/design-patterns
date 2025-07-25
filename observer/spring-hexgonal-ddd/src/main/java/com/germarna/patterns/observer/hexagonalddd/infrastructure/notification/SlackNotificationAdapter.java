package com.germarna.patterns.observer.hexagonalddd.infrastructure.notification;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SlackNotificationSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SlackNotificationAdapter implements SlackNotificationSender {
	@Override
	public void sendNotification(UUID reservationId, String guestName, String roomType) {
		System.out.println("[SLACK] Confirmation sent to " + guestName + " for room type: " + roomType);
	}
}
