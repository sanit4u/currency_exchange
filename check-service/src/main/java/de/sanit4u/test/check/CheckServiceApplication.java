package de.sanit4u.test.check;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CheckServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckServiceApplication.class, args);
	}

}
