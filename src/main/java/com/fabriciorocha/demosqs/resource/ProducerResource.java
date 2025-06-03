package com.fabriciorocha.demosqs.resource;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@RestController
@RequestMapping("/messages")
public class ProducerResource {

    @Autowired private SqsTemplate sqsTemplate;

    @Autowired private SqsAsyncClient client;
    @PostMapping
    ResponseEntity<String> createMessage(@RequestBody String message){

        SendResult result = sqsTemplate.send(sqsSendOptions -> {
            sqsSendOptions.queue("demo");
            sqsSendOptions.payload(message);
        });
        return ResponseEntity.ok(result.message().toString());

    }
}
