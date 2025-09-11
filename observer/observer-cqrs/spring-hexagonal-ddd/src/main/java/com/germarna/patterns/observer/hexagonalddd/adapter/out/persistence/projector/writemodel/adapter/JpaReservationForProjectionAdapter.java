package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.adapter;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.mapper.ReservationSnapshotMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.port.out.ReservationWriteModelLoader;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.writemodel.model.ReservationSnapshot;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JpaReservationForProjectionAdapter implements ReservationWriteModelLoader {

    private final ReservationJpaRepository reservationJpaRepository;
    private final ReservationSnapshotMapper reservationSnapshotMapper;

    @Override
    @Transactional(readOnly = true)
    public ReservationSnapshot load(UUID reservationId) {
        final ReservationJpaEntity found = this.reservationJpaRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + reservationId));
        return reservationSnapshotMapper.toSnapshot(found);
    }
}
