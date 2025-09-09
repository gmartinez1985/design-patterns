package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.helpers;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.RoomType;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.UUID;

public final class JpaSliceTestSupport {
	private JpaSliceTestSupport() {
	}

	/**
	 * Minimal Boot to avoid pulling the app's real ApplicationBoot (e.g., no Mongo
	 * repos).
	 */
	@SpringBootConfiguration
	@EnableAutoConfiguration
	public static class TestBoot {
	}

	/** Enable only our JPA repositories and entities for the slice. */
	@TestConfiguration
	@EnableJpaRepositories(basePackageClasses = {ReservationJpaRepository.class, RoomJpaRepository.class})
	@EntityScan(basePackageClasses = {ReservationJpaEntity.class, RoomJpaEntity.class})
	public static class JpaOnlyConfig {
	}

	// ---- Helpers
	public static RoomJpaEntity persistRoom(RoomJpaRepository rooms, String number, RoomType type) {
		final RoomJpaEntity room = new RoomJpaEntity();
		room.setId(UUID.randomUUID());
		room.setNumber(number);
		room.setType(type);
		return rooms.saveAndFlush(room);
	}

	public static Date tomorrow() {
		final long day = 86_400_000L;
		return new Date(System.currentTimeMillis() + day);
	}

	public static Date dayAfterTomorrow() {
		final long day = 86_400_000L;
		return new Date(System.currentTimeMillis() + 2 * day);
	}
}
