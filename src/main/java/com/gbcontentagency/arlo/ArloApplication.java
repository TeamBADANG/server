package com.gbcontentagency.arlo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArloApplication.class, args);
	}

}
