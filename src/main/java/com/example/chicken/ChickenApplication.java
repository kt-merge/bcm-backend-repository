package com.example.chicken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ChickenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChickenApplication.class, args);
	}

}
