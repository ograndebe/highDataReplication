package com.github.ograndebe.hdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HdpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HdpApplication.class, args);
	}

}
