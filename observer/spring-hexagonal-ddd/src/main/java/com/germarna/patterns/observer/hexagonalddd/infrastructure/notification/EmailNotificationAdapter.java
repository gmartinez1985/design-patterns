package com.germarna.patterns.observer.hexagonalddd.infrastructure.notification;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.EmailNotificationSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailNotificationAdapter implements EmailNotificationSender {
	@Override
	public void sendNotification(UUID reservationId, String guestName, String roomType) {
		System.out.println("[EMAIL] Confirmation sent to " + guestName + " for room type: " + roomType);
	}
}
