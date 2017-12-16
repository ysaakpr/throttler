package com.embrio.test.phonepe.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class KeyForInstance {
    private String withBase;
    private String limit;
    private String current;
    private Specialization.Type type;
    LocalDateTime expire;

    public KeyForInstance(Specialization.Type type, String withBase) {
        this.withBase = withBase;
        this.type = type;
    }

    private KeyForInstance(Specialization.Type type,String limit, String current, LocalDateTime expireAt){
        this.limit = limit;
        this.current = current;
        this.expire = expireAt;
        this.type = type;
    }


    public KeyForInstance forInstance(Integer timeType, LocalDateTime time) {

        String limit=null, current = null;
        switch (timeType) {
            case Calendar.SECOND:
                limit    = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.SECOND);
                current  = limit + ":" + time.toLocalTime().toSecondOfDay();
                break;
            case Calendar.MINUTE:
                limit    = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.MINUTE);
                current  = limit + ":" + time.getMinute();
                break;
            case Calendar.HOUR:
                limit     = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.HOUR);
                current = limit + ":" + time.getHour();
                break;
            case Calendar.DAY_OF_MONTH:
                limit = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.DAY_OF_MONTH);
                current = limit + ":" + time.getDayOfMonth();
                break;
            case Calendar.MONTH:
                limit = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.MONTH);
                current = limit + ":" + time.getMonthValue();
                break;
            default:
                return null;
        }
        return new KeyForInstance(this.type,limit,current, LocalDateTime.now().plusMinutes(10));
    }

    public String getLimit() {
        return limit;
    }

    public String getCurrent() {
        return current;
    }

    public LocalDateTime getExpire() {
        return expire;
    }

    public Specialization.Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "KeyForInstance{" +
                "withBase='" + withBase + '\'' +
                ", limit='" + limit + '\'' +
                ", current='" + current + '\'' +
                ", expire=" + expire +
                '}';
    }
}
