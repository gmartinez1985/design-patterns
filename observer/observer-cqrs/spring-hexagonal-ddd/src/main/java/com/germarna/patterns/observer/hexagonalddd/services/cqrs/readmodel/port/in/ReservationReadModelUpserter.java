package com.germarna.patterns.observer.hexagonalddd.services.cqrs.readmodel.port.in;

import com.germarna.patterns.observer.hexagonalddd.services.cqrs.ReservationProjectionView;

public interface ReservationReadModelUpserter {
	void upsert(ReservationProjectionView document);
}
