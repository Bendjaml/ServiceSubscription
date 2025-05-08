package com.example.ServiceSubscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = "com.example.ServiceSubscription"
)
@EntityScan("com.example.ServiceSubscription.model")
@EnableJpaRepositories("com.example.ServiceSubscription.repository")
class ServiceSubscriptionApplicationTests {
	public static void main(String[] args) {
			SpringApplication.run(ServiceSubscriptionApplication.class, args);
	}
}
