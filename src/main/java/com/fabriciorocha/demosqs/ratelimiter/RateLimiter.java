package com.fabriciorocha.demosqs.ratelimiter;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimiter {
    //key=1239012903 value=0
    int RATE_LIMIT = 2;

    Map<String, Integer> history= new HashMap<>();

    public void increment(String queueName){
        String key = buildKey(queueName);
        if(!history.containsKey(key)){
            history.put(key, 0);
        }
        history.put(key, history.get(key)+1);
    }

    public Integer getValue(String key){
        return history.containsKey(key) ? history.get(key) : 0;
    }

    public boolean canProceed(String queueName){
        String key = buildKey(queueName);
        Integer value = getValue(key);
        return  value < RATE_LIMIT;
    }

    public String buildKey(String queue){
        return queue + LocalDateTime.now().getMinute();
    }

}
