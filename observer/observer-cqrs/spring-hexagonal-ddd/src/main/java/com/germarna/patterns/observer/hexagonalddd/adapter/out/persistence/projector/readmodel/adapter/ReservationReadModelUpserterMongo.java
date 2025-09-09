package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.readmodel.adapter;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.readmodel.port.in.ReservationReadModelUpserter;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.readmodel.mapper.ReservationViewMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.ReservationProjectionView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationReadModelUpserterMongo implements ReservationReadModelUpserter {
	private final ReservationMongoRepository reservationMongoRepository;
	private final ReservationViewMapper reservationViewMapper;
	@Override
	public void upsert(ReservationProjectionView view) {
		final ReservationMongoDocument document = this.reservationViewMapper.toDocument(view);
		this.reservationMongoRepository.save(document);
	}
}
