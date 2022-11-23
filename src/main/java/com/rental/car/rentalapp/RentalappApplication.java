package com.rental.car.rentalapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rental.car.rentalapp.service.rent.repository.*")
public class RentalappApplication {
	public static void main(String[] args) {
		SpringApplication.run(RentalappApplication.class, args);
	}
}
