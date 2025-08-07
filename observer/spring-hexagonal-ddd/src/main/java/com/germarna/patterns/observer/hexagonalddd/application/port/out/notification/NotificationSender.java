package com.germarna.patterns.observer.hexagonalddd.application.port.out.notification;

import java.util.UUID;

public interface NotificationSender {
	void sendNotification(UUID reservationId, String guestName, String roomType);
}
