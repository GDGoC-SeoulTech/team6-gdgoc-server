package com.gdgoc.arcive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArciveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArciveApplication.class, args);
	}

}
