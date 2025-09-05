package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.projection;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.datamodel.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper.ReservationMongoMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.model.ReservationProjectionView;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.port.out.ReservationReadModelUpserter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationReadModelUpserterMongo implements ReservationReadModelUpserter {
	private final ReservationMongoRepository reservationMongoRepository;
	private final ReservationMongoMapper reservationMongoMapper;
	@Override
	public void upsert(ReservationProjectionView view) {
		final ReservationMongoDocument document = this.reservationMongoMapper.toDocument(view);
		this.reservationMongoRepository.save(document);
	}
}
