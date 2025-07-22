package com.germarna.patterns.observer.hexagonalddd;

import com.germarna.patterns.observer.hexagonalddd.domain.model.reservation.Reservation;
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
			Reservation.create("John Doe", "Deluxe Suite");
			System.out.println("---");
			Reservation.create("Jane Doe", "Standard Room");
		};
	}

}
