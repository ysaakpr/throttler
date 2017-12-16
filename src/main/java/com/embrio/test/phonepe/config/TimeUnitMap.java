package com.embrio.test.phonepe.config;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

public final class TimeUnitMap {
    public static BiMap<String,Integer> MAP;

    static {
        MAP = new ImmutableBiMap.Builder<String,Integer>()
                .put("min", Calendar.MINUTE)
                .put("sec", Calendar.SECOND)
                .put("hour", Calendar.HOUR)
                .put("day", Calendar.DAY_OF_MONTH)
                .put("week", Calendar.WEEK_OF_MONTH)
                .put("month", Calendar.MONTH)
                .build();
    }


    public static Integer toTimeUnit(String name) {
        return MAP.get(name);
    }

    public static String toTimeLabel(Integer timeUnit){
        return MAP.inverse().get(timeUnit);
    }

    public static boolean isValidTimeUnit(Integer timeUnit) {
        return MAP.inverse().containsKey(timeUnit);
    }

    public static boolean isValidTimeLabel(String label) {
        return MAP.containsKey(label);
    }

    public static Collection<Integer> getAllTimeUnits() {
        return MAP.values();
    }
}
