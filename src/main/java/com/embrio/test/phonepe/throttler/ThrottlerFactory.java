package com.embrio.test.phonepe.throttler;

import com.embrio.test.phonepe.config.*;
import com.embrio.test.phonepe.store.ThrottlerStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;

public class ThrottlerFactory {
    public Throttler defaultThrottler() throws IOException {
        return withConfig(new DefaultThrottlerConfiguration());
    }

    public Throttler withConfig(ThrottlerConfiguration conf) throws IOException {
        ThrottlerImpl throttler = new ThrottlerImpl();
        throttler.setStorage(conf.getStorage());
        ThrottlerStorage storage = conf.getStorage();

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(conf.getConfigFileName());

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ThrottlerConfig[] throttlerConfigs = mapper.readValue(in, ThrottlerConfig[].class);
        if(throttlerConfigs != null) {
            for(ThrottlerConfig config : throttlerConfigs) {
                KeyInstanceBuilder builder = new KeyInstanceBuilder();
                builder.setClient(config.getClient());

                if(config.getLimit() != null){
                    KeyForInstance clientLevel = builder.build();
                    initLimitForAllTimeUnit(storage,clientLevel, config.getLimit());
                }

                if(config.getSpecialization() != null && !config.getSpecialization().isEmpty()) {
                    for(Specialization specialization : config.getSpecialization()) {
                        if(specialization != null && specialization.getType() != null
                                && specialization.getLimit() != null && !specialization.getLimit().isEmpty()) {
                            builder.setSpecializationType(specialization.getType());
                            for(Map.Entry<String,Limit> entry : specialization.getLimit().entrySet()){
                                if(entry.getKey() != null && !entry.getKey().isEmpty() && entry.getValue() != null) {
                                    builder.setSpecializationValue(entry.getKey());
                                    KeyForInstance spInstance = builder.build();
                                    initLimitForAllTimeUnit(storage, spInstance, entry.getValue());
                                }

                            }
                        }
                    }
                }
            }
        }


        return throttler;
    }

    private void initLimitForAllTimeUnit(ThrottlerStorage storage, KeyForInstance parent, Limit limit) {
        LocalDateTime now = LocalDateTime.now();
        for(Map.Entry<String,Long> entry:limit.entrySet()){
            if(TimeUnitMap.isValidTimeLabel(entry.getKey()) && entry.getValue() != null) {
                KeyForInstance forInstance = parent.forInstance(TimeUnitMap.toTimeUnit(entry.getKey()), now);
                storage.set(forInstance.getLimit(), entry.getValue());
            }
        }
    }
}
