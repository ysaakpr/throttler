package com.embrio.test.phonepe.junit;


import com.embrio.test.phonepe.exceptions.ClientThrottledException;
import com.embrio.test.phonepe.exceptions.ThrottlerLimitBreachedException;
import com.embrio.test.phonepe.throttler.Request;
import com.embrio.test.phonepe.throttler.Throttler;
import com.embrio.test.phonepe.throttler.ThrottlerFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

public class ClientThrottlingTests {
    Throttler throttler;
    @Before
    public void init() throws IOException {
        ThrottlerFactory factory = new ThrottlerFactory();
        throttler = factory.defaultThrottler();
    }

    @Test(expected = ClientThrottledException.class)
    public void clientThrottlingForASecond() throws ThrottlerLimitBreachedException{
        Request request = new Request();
        request.setClient("client1");
        request.setMethod("POST");
        request.setRequestTime(LocalDateTime.now());
        request.setApi("/status");
        for(int i=0;i<3;i++) {
            request.setRequestTime(LocalDateTime.now());
            throttler.throttle(request);
        }
    }

    @Test(expected = ClientThrottledException.class)
    public void clientThrottlingForAMinute() throws ThrottlerLimitBreachedException, InterruptedException{
        Request request = new Request();
        request.setClient("client1");
        request.setMethod("POST");
        request.setApi("/status");
        for(int i=0;i<20;i++) {
            request.setRequestTime(LocalDateTime.now());
            throttler.throttle(request);
            Thread.sleep(1000);
        }
    }

    @Test
    public void NoClientThrottling() throws ThrottlerLimitBreachedException{
        Request request = new Request();
        request.setClient("no-client-registered-in-this-name");
        request.setMethod("POST");
        request.setApi("/status");
        for(int i=0;i<200;i++) {
            request.setRequestTime(LocalDateTime.now());
            throttler.throttle(request);
        }
    }
}
