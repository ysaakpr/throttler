package com.embrio.test.phonepe.throttler;

import com.embrio.test.phonepe.config.Specialization;
import com.embrio.test.phonepe.config.ThrottlerConfig;
import com.embrio.test.phonepe.exceptions.ThrottlerLimitBreachedException;
import com.embrio.test.phonepe.store.ThrottlerStorage;

import java.time.LocalDateTime;
import java.util.List;

public interface Throttler {
    void throttle(String client, Specialization.Type type, String value,LocalDateTime requestTime) throws ThrottlerLimitBreachedException;
    void throttle(Request request) throws ThrottlerLimitBreachedException;
}
