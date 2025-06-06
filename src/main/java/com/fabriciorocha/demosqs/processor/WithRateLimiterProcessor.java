package com.fabriciorocha.demosqs.processor;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

@Service
public class WithRateLimiterProcessor {

    public void receive(Message message) throws InterruptedException {
        System.out.println("Processing message from queue with rate limiter: " + message);
        System.out.println("Finished process message with rate limiter");
    }
}
