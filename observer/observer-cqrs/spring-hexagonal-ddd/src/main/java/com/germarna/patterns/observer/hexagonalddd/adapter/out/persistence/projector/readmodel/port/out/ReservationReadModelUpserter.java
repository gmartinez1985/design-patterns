package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.readmodel.port.out;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.ReservationProjectionView;

public interface ReservationReadModelUpserter {
	void upsert(ReservationProjectionView document);
}
