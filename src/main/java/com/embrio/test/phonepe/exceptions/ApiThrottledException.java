package com.embrio.test.phonepe.exceptions;

import com.embrio.test.phonepe.config.KeyForInstance;

public class ApiThrottledException extends ThrottlerLimitBreachedException {
    public ApiThrottledException(KeyForInstance keyForInstance, Long limitVal, long counter) {
        super(keyForInstance, limitVal, counter);
    }
}
