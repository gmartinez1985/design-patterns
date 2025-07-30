package com.germarna.patterns.observer.hexagonalddd.infrastructure.notification;

import com.germarna.patterns.observer.hexagonalddd.application.notification.port.out.SmsNotificationSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SmsNotificationAdapter implements SmsNotificationSender {
	@Override
	public void sendNotification(UUID reservationId, String guestName, String roomType) {
		System.out.println("[SMS] Confirmation sent to " + guestName + " for room type: " + roomType);
	}
}
