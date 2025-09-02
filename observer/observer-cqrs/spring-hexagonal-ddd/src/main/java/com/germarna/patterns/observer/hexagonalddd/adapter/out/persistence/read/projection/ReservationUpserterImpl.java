package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.projection;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document.ReservationDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.repository.ReservationMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationUpserterImpl implements ReservationUpserter {
	private final ReservationMongoRepository reservationMongoRepository;
	@Override
	public void upsert(ReservationDocument document) {
		this.reservationMongoRepository.save(document);
	}
}
