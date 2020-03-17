package com.brain2.demo;

import java.util.Random;

import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;

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
		System.out.println("lol");
	}

	@Bean
	public CommandLineRunner demo(TopicRepo repository, TopicTagsRepo repository2) {
		return (args) -> {
			// create random object
			Random randomno = new Random();

			// check next Gaussian value
			// IntStream.range(1, 70).forEach(i -> {
			// System.out.println(i);
			// System.out.println("Next Gaussian value: " + randomno.nextGaussian());
			// });
			// System.out.println(Arrays.toString(args));
			// // fetch all customers
			// log.info("Topics found with findAll():");
			// log.info("-------------------------------");

			// for (Topic topic : repository.findAll()) {
			// log.info(topic.toString());
			// }
			// log.info("");

			// // fetch all customers
			// log.info("Tags found with findAll():");
			// log.info("-------------------------------");

			// for (Topictags tag : repository2.findAll()) {
			// log.info(tag.toString());
			// }
			// log.info("");

		};
	}

}
