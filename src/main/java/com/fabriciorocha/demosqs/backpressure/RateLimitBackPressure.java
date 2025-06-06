package com.fabriciorocha.demosqs.backpressure;

import com.fabriciorocha.demosqs.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateLimitBackPressure {

    @Autowired private RateLimiter rateLimiter;

    public boolean shouldWait(){
        return !rateLimiter.canProceed("demo_queue_rate_limiter");
    }
}
