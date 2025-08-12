package com.germarna.patterns.decorator.hexagonalddd;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
	@Bean
	CommandLineRunner startupRunner(SaveReservationUseCase saveReservationUseCase,
			FindReservationUseCase findReservationUseCase,
			ChangeDatesOfReservationUseCase changeDatesOfReservationUseCase, CacheManager cacheManager) {
		return args -> {
			final UUID roomId = UUID.randomUUID();
			final UUID guestId = UUID.randomUUID();
			final Date checkIn = new Date(); // today
			final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days
			final Date updatedCheckIn = new Date(checkIn.getTime() + 5 * 24 * 60 * 60 * 1000); // +5 days
			final Date updatedCheckOut = new Date(checkIn.getTime() + 7 * 24 * 60 * 60 * 1000); // +7 days

			try {
				final UUID reservationId = saveReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut);
				System.out.println();
				final Reservation reservation = findReservationUseCase.findReservation(reservationId);
				System.out.println("✅ Reservation found successfully: " + reservation + "\n");
				final Reservation cachedReservation = findReservationUseCase.findReservation(reservationId);
				System.out.println("✅ Reservation found successfully: " + cachedReservation + "\n");
				final Reservation updatedReservation = changeDatesOfReservationUseCase.changeDatesOfReservation(
						cachedReservation.getReservationId(), updatedCheckIn, updatedCheckOut);
				System.out.println("✅ Reservation updated successfully: " + updatedReservation + "\n");
				final Reservation updatedCachedReservation = findReservationUseCase.findReservation(reservationId);
				System.out.println("✅ Reservation found successfully: " + updatedCachedReservation + "\n");
				printCache(cacheManager);

			} catch (final Exception e) {
				System.err.println("❌ Error creating reservation: " + e.getMessage());
			}
		};
	}
	private static void printCache(CacheManager cacheManager) {
		final var cache = cacheManager.getCache("reservations");
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
