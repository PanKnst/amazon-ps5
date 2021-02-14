package com.amazon.ps5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Ps5Application {

	public static void main(String[] args) {
		SpringApplication.run(Ps5Application.class, args);
	}

}
