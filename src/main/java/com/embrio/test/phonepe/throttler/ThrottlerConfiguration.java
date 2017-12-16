package com.embrio.test.phonepe.throttler;

import com.embrio.test.phonepe.store.ThrottlerStorage;

public class ThrottlerConfiguration {
    private final ThrottlerStorage storage;
    private final String configFileName;
    //Need to be built as a builder model in order to build different configuration models
    //Currently using simple constructor based approach


    public ThrottlerConfiguration(String file, ThrottlerStorage storage) {
        this.configFileName = file;
        this.storage = storage;
    }

    public ThrottlerStorage getStorage() {
        return storage;
    }

    public String getConfigFileName() {
        return configFileName;
    }
}
