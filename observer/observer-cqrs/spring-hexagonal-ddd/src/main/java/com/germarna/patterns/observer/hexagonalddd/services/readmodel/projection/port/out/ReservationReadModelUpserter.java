package com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.port.out;

import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.model.ReservationProjectionView;

public interface ReservationReadModelUpserter {
	void upsert(ReservationProjectionView document);
}
