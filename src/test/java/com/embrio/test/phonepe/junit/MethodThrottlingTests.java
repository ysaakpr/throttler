package com.embrio.test.phonepe.junit;


import com.embrio.test.phonepe.exceptions.ApiThrottledException;
import com.embrio.test.phonepe.exceptions.MethodeThrottledException;
import com.embrio.test.phonepe.exceptions.ThrottlerLimitBreachedException;
import com.embrio.test.phonepe.throttler.Request;
import com.embrio.test.phonepe.throttler.Throttler;
import com.embrio.test.phonepe.throttler.ThrottlerFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class MethodThrottlingTests {
    Throttler throttler;
    @Before
    public void init() throws IOException {
        ThrottlerFactory factory = new ThrottlerFactory();
        throttler = factory.defaultThrottler();
    }

    @Test(expected = MethodeThrottledException.class)
    public void methodThrottlingForASecond() throws ThrottlerLimitBreachedException{
        Request request = new Request();
        request.setClient("client3");
        request.setMethod("POST");
        request.setRequestTime(LocalDateTime.now());
        request.setApi("/status");
        for(int i=0;i<3;i++) {
            throttler.throttle(request);
        }
    }

    @Test(expected = MethodeThrottledException.class)
    public void methodThrottlingForAMinute() throws ThrottlerLimitBreachedException, InterruptedException {
        Request request = new Request();
        request.setClient("client3");
        request.setMethod("POST");
        request.setRequestTime(LocalDateTime.now());
        request.setApi("/status");
        for(int i=0;i<20;i++) {
            throttler.throttle(request);
            Thread.sleep(2000);
        }
    }

    @Test()
    public void methodCallsWithoutExceedingLimit() throws ThrottlerLimitBreachedException,InterruptedException {
        Request request = new Request();
        request.setClient("client3");
        request.setMethod("POST");
        request.setRequestTime(LocalDateTime.now());
        request.setApi("/status");
        for(int i=0;i<10;i++) {
            request.setRequestTime(LocalDateTime.now());
            throttler.throttle(request);
            Thread.sleep(2000);
        }
    }

    @Test()
    public void noMethodThrottling() throws ThrottlerLimitBreachedException,InterruptedException {
        Request request = new Request();
        request.setClient("client3");
        request.setMethod("GET");
        request.setRequestTime(LocalDateTime.now());
        request.setApi("/no-such-method-configured");
        for(int i=0;i<100;i++) {
            request.setRequestTime(LocalDateTime.now());
            throttler.throttle(request);
        }
    }
}
