package com.embrio.test.phonepe.exceptions;

import com.embrio.test.phonepe.config.KeyForInstance;

public class ClientThrottledException extends ThrottlerLimitBreachedException {
    public ClientThrottledException(KeyForInstance keyForInstance, Long limitVal, long counter) {
        super(keyForInstance, limitVal, counter);
    }
}
