package com.embrio.test.phonepe.config;

import com.embrio.test.phonepe.config.KeyForInstance;
import com.embrio.test.phonepe.config.Specialization;

import java.time.LocalDateTime;
import java.util.Calendar;

public class KeyInstanceBuilder {
    private String client;
    private Specialization.Type specializationType;
    private String specializationValue;


    public KeyInstanceBuilder setClient(String client) {
        this.client = client;
        return this;
    }

    public KeyInstanceBuilder setSpecializationType(Specialization.Type specializationType) {
        this.specializationType = specializationType;
        return this;

    }

    public KeyInstanceBuilder setSpecializationValue(String specializationValue) {
        this.specializationValue = specializationValue;
        return this;
    }

    public KeyForInstance build() {
        if(client == null) {
            throw new RuntimeException("You must provide a client name");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(client);

        if(specializationType != null && specializationValue != null) {
            sb.append(":");
            sb.append(specializationType);
            sb.append(":");
            sb.append(specializationValue);
        }

        return new KeyForInstance(specializationType,sb.toString());
    }
}
