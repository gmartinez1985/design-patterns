package com.germarna.patterns.decorator.hexagonalddd;

import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
	@Bean
	CommandLineRunner startupRunner(CreateReservationUseCase createReservationUseCase) {
		return args -> {
			final UUID roomId = UUID.randomUUID();
			final UUID guestId = UUID.randomUUID();
			final Date checkIn = new Date(); // today
			final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days

			createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut);
		};
	}

}
