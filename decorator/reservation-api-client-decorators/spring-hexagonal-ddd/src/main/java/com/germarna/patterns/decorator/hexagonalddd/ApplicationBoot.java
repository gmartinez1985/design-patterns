package com.germarna.patterns.decorator.hexagonalddd;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableCaching(proxyTargetClass = true)
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Bean
	CommandLineRunner startupRunner(CreateReservationUseCase createReservationUseCase, CacheManager cacheManager) {
		return args -> {
			final UUID roomId = UUID.randomUUID();
			final UUID guestId = UUID.randomUUID();
			final Date checkIn = new Date();
			final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000);

			UUID cachedReservationId = null;

			for (int i = 1; i <= 5; i++) {
				System.out.println("========== Attempt " + i + " ==========");
				try {
					createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut, UUID.randomUUID());
					System.out.println("✅ Reservation created successfully.");

				} catch (final Exception e) {
					System.out.println("❌ Reservation failed: " + e.getMessage());
				}
				printCache(cacheManager);
				Thread.sleep(1000);
			}

			System.out.println("Waiting for CB to move to half-open...");
			Thread.sleep(4000); // Wait more than circuit breaker waitDurationInOpenState configuration value

			System.out.println("========== Final Attempt ==========");
			try {
				cachedReservationId = UUID.randomUUID();
				createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut, cachedReservationId);
				System.out.println("✅ Final reservation created successfully.");
			} catch (final Exception e) {
				System.out.println("❌ Final reservation failed: " + e.getMessage());
			}
			printCache(cacheManager);

			System.out.println("\n========== Cache HIT Attempt ==========");
			try {
				System.out.println("Cache HIT Attempt: " + cachedReservationId);
				createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut, cachedReservationId);
				System.out.println("✅ Reservation retrieved from cache successfully.");
			} catch (final Exception e) {
				System.out.println("❌ Cache HIT attempt failed: " + e.getMessage());
			}

			System.out.println("\n========== Cache Statistics ==========");
			printCache(cacheManager);
		};
	}

	private static void printCache(CacheManager cacheManager) {
		final var cache = cacheManager.getCache("payments");
		if (cache instanceof CaffeineCache caffeineCache) {
			final var stats = caffeineCache.getNativeCache().stats();
			System.out.println("📊 Cache Hits:   " + stats.hitCount());
			System.out.println("📉 Cache Misses: " + stats.missCount());

			// Mostrar contenido actual de la cache (claves y valores)
			final var nativeCache = caffeineCache.getNativeCache();
			System.out.println("🔑 Cache content:");
			nativeCache.asMap().forEach((key, value) -> System.out.println("  - Key: " + key + ", Value: " + value));
			System.out.println();
		} else {
			System.out.println("⚠️  Cache is not a CaffeineCache. Stats unavailable.");
		}
	}
}
