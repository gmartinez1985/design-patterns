package com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class JdbcAuditUtils {
	private final JdbcTemplate jdbc;

	public JdbcAuditUtils(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	// ----- Envers -----
	public int enversAddCount(UUID resId) {
		return this.getInt("SELECT COUNT(*) FROM RESERVATIONS_AUD WHERE RESERVATION_ID = ? AND REVTYPE = 0", resId);
	}

	public int enversModCount(UUID resId) {
		return this.getInt("SELECT COUNT(*) FROM RESERVATIONS_AUD WHERE RESERVATION_ID = ? AND REVTYPE = 1", resId);
	}

	public long latestRevision(UUID resId) {
		final Long rev = this.jdbc.queryForObject("SELECT MAX(REV) FROM RESERVATIONS_AUD WHERE RESERVATION_ID = ?",
				Long.class, resId);
		assertNotNull(rev, "Expected a revision number in RESERVATIONS_AUD");
		return rev;
	}

	public void assertHasMatchingRevinfo(long rev) {
		final int cnt = this.getInt("SELECT COUNT(*) FROM REVINFO WHERE REV = ?", rev);
		assertEquals(1, cnt, "Expected a matching REVINFO row");
	}

	// ----- Custom audit decorator -----
	public int customAuditCount(UUID resId, String actionType) {
		return this.getInt(
				"SELECT COUNT(*) FROM RESERVATIONS_CUSTOM_AUDIT WHERE RESERVATION_ID = ? AND ACTION_TYPE = ?", resId,
				actionType);
	}

	// ----- helpers -----
	private int getInt(String sql, Object... args) {
		final Integer val = this.jdbc.queryForObject(sql, Integer.class, args);
		return (val == null) ? 0 : val;
	}
}
