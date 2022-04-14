package com.reactivespring.kafkademo.reactivespringkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactivespringkafkaApplication {

	public static void main(String[] args) {
		final SpringApplication application = new SpringApplication(ReactivespringkafkaApplication.class);
		application.setBannerMode(Banner.Mode.CONSOLE);
		application.setWebApplicationType(WebApplicationType.REACTIVE);
		application.run(args);
	}

}
