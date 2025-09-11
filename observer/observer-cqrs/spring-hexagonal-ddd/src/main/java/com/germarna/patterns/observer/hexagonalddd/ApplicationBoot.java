package com.germarna.patterns.observer.hexagonalddd;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.RoomType;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.FindReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Bean
	@ConditionalOnProperty(name = "app.startup-runner.enabled", havingValue = "true", matchIfMissing = false)
	CommandLineRunner startupRunner(CreateReservationUseCase saveReservationUseCase,
			FindReservationUseCase findReservationUseCase, RoomJpaRepository roomJpaRepository,
			ReservationMongoRepository reservationMongoRepository) {
		return args -> {
			final UUID roomId = UUID.fromString("59db0ef6-dfc2-458c-b405-85e7a8a56602");
			final String guestName = "John Doe";
			final String guestEmail = "johndoe@email.com";
			final Date checkIn = new Date(); // today
			final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days

			prepareDatabaseContent(roomJpaRepository, reservationMongoRepository, roomId);

			final UUID reservationId = saveReservationUseCase.createReservation(roomId, guestName, guestEmail, checkIn,
					checkOut);
			System.out.println();
			final ReservationView reservation = findReservationUseCase.findReservation(reservationId);
			System.out.println("✅ Reservation found successfully: " + reservation + "\n");
		};
	}

	private static void prepareDatabaseContent(RoomJpaRepository roomJpaRepository,
			ReservationMongoRepository reservationMongoRepository, UUID roomId) {
		reservationMongoRepository.deleteAll();
		roomJpaRepository.save(new RoomJpaEntity(roomId, "101", RoomType.SINGLE));
	}
}
