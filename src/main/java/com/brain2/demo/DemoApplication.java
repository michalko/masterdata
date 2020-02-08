package com.brain2.demo;

import java.util.Arrays;

import com.brain2.demo.models.Topic;
import com.brain2.demo.models.Topictags;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		System.out.println("HAHAHAHAHA 222222222 666666666666666");
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("HAHAHAHAHA2 11111111111");
		System.out.println("HAHAHAHAHA2 2222 555 333 99999999999");
	}

	@Bean
	public CommandLineRunner demo(TopicRepo repository, TopicTagsRepo repository2) {
		return (args) -> {

			System.out.println(Arrays.toString(args));
			// fetch all customers
			log.info("Topics found with findAll():");
			log.info("-------------------------------");

			for (Topic topic : repository.findAll()) {
				log.info(topic.toString());
			}
			// log.info("");

			// // fetch all customers
			// log.info("Tags found with findAll():");
			// log.info("-------------------------------");

			// for (Topictags tag : repository2.findAll()) {
			// 	log.info(tag.toString());
			// }
			// log.info("");

		};
	}

}
