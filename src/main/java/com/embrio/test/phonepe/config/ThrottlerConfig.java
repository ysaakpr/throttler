package com.embrio.test.phonepe.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThrottlerConfig {
    String client;
    Limit limit;
    List<Specialization> specialization;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public List<Specialization> getSpecialization() {
        return specialization;
    }

    public void setSpecialization(List<Specialization> specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "ThrottlerConfig{" +
                "client='" + client + '\'' +
                ", limit=" + limit +
                ", specialization=" + specialization +
                '}';
    }
}
