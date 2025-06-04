package com.fabriciorocha.demosqs.processor;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

@Service
public class WithDlqProcessor {

    public void receive(Message message) throws InterruptedException {
        System.out.println("WITH DLQ PROCESSOR RECEIVED");
        Thread.sleep(2000);
        System.out.println("FAILED");
        throw new RuntimeException();
    }
}
