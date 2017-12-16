package com.embrio.test.phonepe.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        LocalDateTime expireAt;
        switch (timeType) {
            case Calendar.SECOND:
                limit    = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.SECOND);
                current  = limit + ":" + time.toLocalTime().toSecondOfDay();
                expireAt = LocalDateTime.from(time).plusSeconds(2).truncatedTo(ChronoUnit.SECONDS);
                break;
            case Calendar.MINUTE:
                limit    = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.MINUTE);
                current  = limit + ":" + time.getMinute();
                expireAt = LocalDateTime.from(time).plusMinutes(1).truncatedTo(ChronoUnit.MINUTES);
                break;
            case Calendar.HOUR:
                limit     = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.HOUR);
                current = limit + ":" + time.getHour();
                expireAt = LocalDateTime.from(time).plusHours(1).truncatedTo(ChronoUnit.HOURS);
                break;
            case Calendar.DAY_OF_MONTH:
                limit = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.DAY_OF_MONTH);
                current = limit + ":" + time.getDayOfMonth();
                expireAt = LocalDateTime.from(time).plusDays(1).truncatedTo(ChronoUnit.DAYS);
                break;
            case Calendar.MONTH:
                limit = withBase + ":" + TimeUnitMap.toTimeLabel(Calendar.MONTH);
                current = limit + ":" + time.getMonthValue();
                expireAt = LocalDateTime.from(time).plusMonths(1).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);
                break;
            default:
                return null;
        }
        return new KeyForInstance(this.type,limit,current, expireAt);
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
