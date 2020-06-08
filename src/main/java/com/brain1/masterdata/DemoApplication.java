package com.brain1.masterdata;

import com.brain1.masterdata.repos.TopicRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// @EnableCaching
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TopicRepo repo) {
		return (args) -> {
			repo.findByName("Physics").ifPresent(tag -> System.out.println(tag.getId()));
		};
	}


}
