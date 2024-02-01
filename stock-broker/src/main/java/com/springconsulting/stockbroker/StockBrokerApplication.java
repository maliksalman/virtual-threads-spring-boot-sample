package com.springconsulting.stockbroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class StockBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockBrokerApplication.class, args);
	}

}
