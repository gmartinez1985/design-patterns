package com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers;

import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class CacheAssertions {
	private CacheAssertions() {
	}

	public static void assertPresent(Cache cache, Object key) {
		assertNotNull(cache, "Cache must not be null");
		assertNotNull(cache.get(key), "Expected entry in cache for key: " + key);
	}

	public static void assertCacheHitCount(Cache cache, long expected) {
		if (cache instanceof CaffeineCache cc) {
			final long hits = cc.getNativeCache().stats().hitCount();
			assertEquals(expected, hits, "Unexpected cache hit count");
		}
	}

	public static void assertCacheMissCount(Cache cache, long expected) {
		if (cache instanceof CaffeineCache cc) {
			final long misses = cc.getNativeCache().stats().missCount();
			assertEquals(expected, misses, "Unexpected cache miss count");
		}
	}
	public static void assertCacheSize(Cache cache, int expectedSize) {
		if (cache instanceof CaffeineCache cc) {
			assertEquals(expectedSize, cc.getNativeCache().asMap().size(), "Unexpected cache size");
		}
	}
}
