package com.embrio.test.phonepe.config;

import java.util.HashMap;
import java.util.Map;

public class Specialization {
    public enum Type {
        METHOD,
        API
    }

    Type type;
    Map<String,Limit> limit;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, Limit> getLimit() {
        return limit;
    }

    public void setLimit(Map<String, Limit> limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Specialization{" +
                "type=" + type +
                ", limit=" + limit +
                '}';
    }
}
