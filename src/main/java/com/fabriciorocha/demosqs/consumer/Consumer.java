package com.fabriciorocha.demosqs.consumer;

import com.fabriciorocha.demosqs.processor.WithDlqProcessor;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;


@Component
public class Consumer {

    @Autowired private SqsTemplate sqsTemplate;

    @Autowired private WithDlqProcessor withDlqProcessor;

    @SqsListener("demo")
    public void listen(Message message){
        System.out.println("LISTENED MESSAGE " + message);
    }

    @SqsListener("demo_queue_with_dlq")
    public void listenWithDlq(Message message) throws InterruptedException {
        withDlqProcessor.receive(message);
    }

    @SqsListener("demo_dlq")
    public void listenDlq(Message message) {
        System.out.println("LISTENED MESSAGE FROM DlQ " + message);
    }
}
