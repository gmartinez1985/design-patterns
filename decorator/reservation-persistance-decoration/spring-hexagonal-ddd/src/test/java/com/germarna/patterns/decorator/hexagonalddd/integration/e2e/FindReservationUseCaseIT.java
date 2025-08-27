package com.germarna.patterns.decorator.hexagonalddd.integration.e2e;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.JdbcAuditUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.CacheAssertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"app.startup-runner.enabled=false", "spring.cache.caffeine.spec=recordStats"})
@DisplayName("IT – FindReservationUseCase (custom audit + cache)")
class FindReservationUseCaseIT {

	private static final String CACHE_NAME = "reservations";
	private static final UUID RES_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	private static final UUID ROOM_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
	private static final UUID GUEST_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

	@Autowired
	private FindReservationUseCase findReservationUseCase;
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private CacheManager cacheManager;

	private JdbcAuditUtils audit;
	private Cache cache;

	@BeforeEach
	void setUp() {
		this.audit = new JdbcAuditUtils(this.jdbc);
		this.cache = this.cacheManager.getCache(CACHE_NAME);
		assertNotNull(this.cache, "Expected cache named '" + CACHE_NAME + "'");
		this.cache.clear();
	}

	@Test
	@DisplayName("findReservation is audited on each call and second call hits the cache")
	@Sql(scripts = "/sql/find-reservation/seed.sql")
	@Sql(scripts = "/sql/find-reservation/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void findReservationAuditsAndCaches() {
		// GIVEN
		final int beforeFindAudits = this.countFindAudits(RES_ID);
		long cacheHitsCount = 0;

		// WHEN
		final Reservation firstReservationFindResult = this.find(RES_ID);

		// THEN
		cacheHitsCount += this.assertFirstReservationFindResult(firstReservationFindResult, beforeFindAudits);

		final Reservation secondReservationFindResult = this.find(RES_ID);
		cacheHitsCount++;
		this.assertSameAggregate(firstReservationFindResult, secondReservationFindResult);
		this.assertCustomAudit(beforeFindAudits, 2, "Second call must be audited too");
		assertCacheHitCount(this.cache, cacheHitsCount);
		assertCacheMissCount(this.cache, 1);
		assertCacheSize(this.cache, 1);
	}

	// ----------------- HELPERS -----------------

	private Reservation find(UUID id) {
		return this.findReservationUseCase.findReservation(id);
	}

	private long assertFirstReservationFindResult(Reservation firstReservationFindResult, int beforeFindAudits) {
		assertNotNull(firstReservationFindResult, "Reservation should not be null");
		assertAll(() -> assertEquals(RES_ID, firstReservationFindResult.getReservationId()),
				() -> assertEquals(ROOM_ID, firstReservationFindResult.getRoomId()),
				() -> assertEquals(GUEST_ID, firstReservationFindResult.getGuestId()));
		assertEquals(beforeFindAudits + 1, this.countFindAudits(RES_ID), "First call must be audited");
		assertPresent(this.cache, RES_ID);
		return 1L;
	}

	private void assertSameAggregate(Reservation a, Reservation b) {
		assertNotNull(a);
		assertNotNull(b);
		assertEquals(a.getReservationId(), b.getReservationId());
	}

	private void assertCustomAudit(int beforeFindAudits, int expectedDelta, String message) {
		assertEquals(beforeFindAudits + expectedDelta, this.countFindAudits(RES_ID), message);
	}

	private int countFindAudits(UUID id) {
		return this.audit.customAuditCount(id, ReservationActionType.FIND.name());
	}
}
