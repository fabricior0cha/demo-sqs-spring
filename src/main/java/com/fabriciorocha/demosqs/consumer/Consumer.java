package com.fabriciorocha.demosqs.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;


@Component
public class Consumer {

    @Autowired private SqsTemplate sqsTemplate;

    @SqsListener("demo")
    public void listen(Message message){
        System.out.println("LISTENED MESSAGE " + message);
    }
}
