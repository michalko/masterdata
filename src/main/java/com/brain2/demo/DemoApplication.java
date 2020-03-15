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
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
	// @MessagingGateway(defaultRequestChannel="foo.input")
	// public static interface FooService {
	// 	String foo(String request);
	// }

	
	// @Bean
	// IntegrationFlow foo() {
	// 	return f -> f
	// 		.transform("payload + payload")
	// 		.handle(String.class, (p, h) -> p.toUpperCase())
	// 		.routeToRecipients(r ->
	// 			r.recipient("bridgeToNowhere", "true")
	// 			 .recipient("smtpChannel", "true"));
	// }

	// @BridgeTo
	// @Bean
	// public MessageChannel bridgeToNowhere() {
	// 	return new DirectChannel();
	// }

	// @Bean
	// public MessageChannel smtpChannel() {
	// 	return new DirectChannel();
	// }

	
    // @Bean
    // public IntegrationFlow sendMailFlow() {
    //     return IntegrationFlows.from("sendMailChannel")
    //             .enrichHeaders(Mail.headers()
    //                     .subjectFunction(m -> "foo")
    //                     .from("foo@bar")
    //                     .toFunction(m -> new String[] { "bar@baz" }))
    //             .handle(Mail.outboundAdapter("gmail")
    //                         .port(smtpServer.getPort())
    //                         .credentials("user", "pw")
    //                         .protocol("smtp"))x`
    //             .get();
	// }

	
	@Bean
	public CommandLineRunner demo(TopicRepo repository, TopicTagsRepo repository2) {
		return (args) -> {
			// create random object
			Random randomno = new Random();
		  
			// check next Gaussian value  
			// IntStream.range(1, 70).forEach(i -> {
			// 	System.out.println(i);
			// 	System.out.println("Next Gaussian value: " + randomno.nextGaussian());
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
