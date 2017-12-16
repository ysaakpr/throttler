package com.embrio.test.phonepe.throttler;

import com.embrio.test.phonepe.config.KeyForInstance;
import com.embrio.test.phonepe.config.KeyInstanceBuilder;
import com.embrio.test.phonepe.config.Specialization;
import com.embrio.test.phonepe.config.TimeUnitMap;
import com.embrio.test.phonepe.exceptions.ApiThrottledException;
import com.embrio.test.phonepe.exceptions.ClientThrottledException;
import com.embrio.test.phonepe.exceptions.MethodeThrottledException;
import com.embrio.test.phonepe.exceptions.ThrottlerLimitBreachedException;
import com.embrio.test.phonepe.store.ThrottlerStorage;
import com.embrio.test.phonepe.throttler.Throttler;

import java.time.LocalDateTime;

public class ThrottlerImpl implements Throttler {
    ThrottlerStorage storage = null;

    ThrottlerImpl() {
    }

    public ThrottlerStorage getStorage() {
        return storage;
    }

    public void setStorage(ThrottlerStorage storage) {
        this.storage = storage;
    }

    @Override
    public void throttle(String client, Specialization.Type type, String value, LocalDateTime requestTime) throws ThrottlerLimitBreachedException {
        if (requestTime == null) {
            requestTime = LocalDateTime.now();
        }

        KeyInstanceBuilder builder = new KeyInstanceBuilder();
        builder.setClient(client);
        KeyForInstance clientInstance = builder.build();
        validateForAllTimeUnit(clientInstance, requestTime);

        builder.setSpecializationType(type);
        builder.setSpecializationValue(value);
        KeyForInstance specializationInstance = builder.build();
        validateForAllTimeUnit(specializationInstance, requestTime);
    }

    @Override
    public void throttle(Request request) throws ThrottlerLimitBreachedException {
        if(request.getRequestTime() == null) {
            request.setRequestTime(LocalDateTime.now());
        }

        KeyInstanceBuilder builder = new KeyInstanceBuilder();
        builder.setClient(request.getClient());
        KeyForInstance clientInstance = builder.build();
        validateForAllTimeUnit(clientInstance, request.getRequestTime());

        builder.setSpecializationType(Specialization.Type.METHOD);
        builder.setSpecializationValue(request.getMethod());
        KeyForInstance methodInstance = builder.build();
        validateForAllTimeUnit(methodInstance, request.getRequestTime());

        builder.setSpecializationType(Specialization.Type.API);
        builder.setSpecializationValue(request.getApi());
        KeyForInstance apiInstance = builder.build();
        validateForAllTimeUnit(apiInstance, request.getRequestTime());

    }

    private void validateForAllTimeUnit(KeyForInstance config, LocalDateTime requestTime) throws ThrottlerLimitBreachedException {
        /*
        TODO: Can be optimized in such a way that for an instance what all time units are enabled for
        limit can be added to storage and only that time units need to be checked, instead of doing blind iteration on the time units
         */
        for(Integer timeUnit : TimeUnitMap.getAllTimeUnits()) {
            KeyForInstance keyForInstance = config.forInstance(timeUnit, requestTime);
            validate(keyForInstance);
        }
    }

    private void validate(KeyForInstance keyForInstance) throws ThrottlerLimitBreachedException{
        if(keyForInstance == null) return;
        String limit = keyForInstance.getLimit();
        String current = keyForInstance.getCurrent();

        Long limitVal = storage.get(limit);
        if(limitVal != null) {
            long counter = storage.incr(current, keyForInstance.getExpire());
            if(counter > limitVal) {
                if(keyForInstance.getType() == null) {
                    throw new ClientThrottledException(keyForInstance,limitVal, counter);
                }

                switch (keyForInstance.getType()) {
                    case API:
                        throw new ApiThrottledException(keyForInstance,limitVal,counter);
                    case METHOD:
                        throw new MethodeThrottledException(keyForInstance,limitVal, counter);
                    default:
                        throw new ClientThrottledException(keyForInstance,limitVal, counter);
                }
            }
        }
    }
}
