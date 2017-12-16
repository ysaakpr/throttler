package com.embrio.test.phonepe.exceptions;

import com.embrio.test.phonepe.config.KeyForInstance;

public class MethodeThrottledException extends ThrottlerLimitBreachedException {
    public MethodeThrottledException(KeyForInstance keyForInstance, Long limitVal, long counter) {
        super(keyForInstance, limitVal, counter);
    }
}
