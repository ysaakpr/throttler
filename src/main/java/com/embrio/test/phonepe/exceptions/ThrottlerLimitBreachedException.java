package com.embrio.test.phonepe.exceptions;

import com.embrio.test.phonepe.config.KeyForInstance;

public class ThrottlerLimitBreachedException extends Exception{
    public ThrottlerLimitBreachedException() {
    }

    public ThrottlerLimitBreachedException(String message) {
        super(message);
    }

    public ThrottlerLimitBreachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThrottlerLimitBreachedException(Throwable cause) {
        super(cause);
    }

    public ThrottlerLimitBreachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ThrottlerLimitBreachedException(KeyForInstance keyForInstance, Long limitVal, long counter) {
        this(String.format("Request have been throttled for %s, Max Limit: %d, Current Hit Count: %d", keyForInstance.getLimit(), limitVal, counter));
    }
}
