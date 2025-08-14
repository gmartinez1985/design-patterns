package com.germarna.patterns.observer.hexagonalddd;

import com.germarna.patterns.observer.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
	@Bean
	CommandLineRunner startupRunner() {
		return args -> {
			// This is where we would inject the use case and execute it. How to do that is
			// showcased in the Decorator pattern example.
			final CreateReservationUseCase createReservationUseCase = new CreateReservationService();

			createReservationUseCase.createReservation("John Doe", "Deluxe Suite");
			System.out.println("---");
			createReservationUseCase.createReservation("Jane Doe", "Standard Room");
		};
	}

}
