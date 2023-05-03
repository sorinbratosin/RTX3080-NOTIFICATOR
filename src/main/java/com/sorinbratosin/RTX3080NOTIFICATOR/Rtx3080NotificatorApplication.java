package com.sorinbratosin.RTX3080NOTIFICATOR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Rtx3080NotificatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(Rtx3080NotificatorApplication.class, args);
	}

}
