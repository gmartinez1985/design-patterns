package com.germarna.patterns.observer.hexagonalddd.application.notification.port.out;

import java.util.UUID;

public interface NotificationSender {
	void sendNotification(UUID reservationId, String guestName, String roomType);
}
