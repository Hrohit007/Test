package com.ge.cab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
public class CabDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabDemoApplication.class, args);
		
	}
}
