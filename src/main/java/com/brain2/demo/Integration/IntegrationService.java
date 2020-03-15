package com.brain2.demo.Integration;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class IntegrationService {

    @ServiceActivator(inputChannel = "integration.request.channel")
    public void logMail(Message<String> msg) {

        System.out.println("YOLO");
        System.out.println(msg.toString());

    }

}