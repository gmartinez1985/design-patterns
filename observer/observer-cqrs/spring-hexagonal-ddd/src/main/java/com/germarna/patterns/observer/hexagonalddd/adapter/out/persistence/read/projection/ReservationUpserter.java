package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.projection;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document.ReservationDocument;

public interface ReservationUpserter {
	void upsert(ReservationDocument document);
}
