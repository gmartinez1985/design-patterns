package com.germarna.patterns.observer.hexagonalddd;

import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationBoot {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}
	@Bean
	@ConditionalOnProperty(name = "app.startup-runner.enabled", havingValue = "true", matchIfMissing = false)
	CommandLineRunner startupRunner(CreateReservationUseCase createReservationUseCase) {
		return args -> {
			createReservationUseCase.createReservation("John Doe", "Deluxe Suite");
			System.out.println("---");
			createReservationUseCase.createReservation("Jane Doe", "Standard Room");
		};
	}

}
