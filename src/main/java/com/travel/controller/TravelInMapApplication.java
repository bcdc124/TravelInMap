package com.travel.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.travel")
@EntityScan(basePackages = "com.travel.entity")
@EnableJpaRepositories(basePackages = "com.travel.persistence")
public class TravelInMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelInMapApplication.class, args);
	}

}
