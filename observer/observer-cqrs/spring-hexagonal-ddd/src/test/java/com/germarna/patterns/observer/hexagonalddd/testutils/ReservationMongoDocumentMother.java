package com.germarna.patterns.observer.hexagonalddd.testutils;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public final class ReservationMongoDocumentMother {
    private static final UUID RESERVATION_ID = UUID.fromString("d1de7f0c-0ede-4609-860b-474668df6c7e");
    private static final UUID NON_EXISTING_RESERVATION_ID = UUID.fromString("8fcbf102-f85e-46dd-8d16-0fe6c1ae49af");

    public static ReservationMongoDocument createMongoDocument() {
        return new ReservationMongoDocument(
                RESERVATION_ID,
                new ReservationMongoDocument.RoomSnapshot(
                        UUID.randomUUID(),
                        "SINGLE",
                        "101"),
                "Alan Turing",
                "alan@example.com",
                Date.from(Instant.parse("2025-09-09T00:00:00Z")),
                Date.from(Instant.parse("2025-09-11T00:00:00Z")),
                Instant.now()
        );
    }

    public static UUID nonExistingReservationId() {
        return NON_EXISTING_RESERVATION_ID;
    }
}
