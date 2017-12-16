package com.embrio.test.phonepe.throttler;

import com.embrio.test.phonepe.store.LocalStorage;

public class DefaultThrottlerConfiguration extends ThrottlerConfiguration{
    public DefaultThrottlerConfiguration() {
        super("throttler.yml",new LocalStorage());
    }

}
