package com.fabriciorocha.demosqs.consumer;

import com.fabriciorocha.demosqs.backpressure.RateLimitBackPressure;
import com.fabriciorocha.demosqs.processor.WithDlqProcessor;
import com.fabriciorocha.demosqs.processor.WithRateLimiterProcessor;
import com.fabriciorocha.demosqs.ratelimiter.RateLimiter;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

import java.time.LocalDateTime;


@Component
public class Consumer {


    @Autowired private SqsTemplate sqsTemplate;

    @Autowired private WithDlqProcessor withDlqProcessor;

    @Autowired private WithRateLimiterProcessor rateLimiterProcessor;

    @Autowired private RateLimiter rateLimiter;

    @Autowired private RateLimitBackPressure backPressure;
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

    @SqsListener("demo_queue_rate_limiter")
    public void listenRateLimiter(Message message) throws InterruptedException {
        while (backPressure.shouldWait()){
            System.out.println("Waiting to consume other messages");
            Thread.sleep(5000);
        }
        rateLimiter.increment("demo_queue_rate_limiter");
        rateLimiterProcessor.receive(message);
        System.out.println("LISTENED MESSAGE FROM RATE LIMITER QUEUE ");
    }


}
