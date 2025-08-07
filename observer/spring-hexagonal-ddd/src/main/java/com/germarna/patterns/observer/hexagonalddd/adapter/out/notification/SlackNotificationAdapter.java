package com.germarna.patterns.observer.hexagonalddd.adapter.out.notification;

import com.germarna.patterns.observer.hexagonalddd.application.port.out.notification.SlackNotificationSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SlackNotificationAdapter implements SlackNotificationSender {
	@Override
	public void sendNotification(UUID reservationId, String guestName, String roomType) {
		System.out.println("[SLACK] Confirmation sent to " + guestName + " for room type: " + roomType);
	}
}
