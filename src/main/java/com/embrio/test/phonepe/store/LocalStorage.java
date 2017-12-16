package com.embrio.test.phonepe.store;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.cache2k.CacheEntry;
import org.cache2k.expiry.ExpiryPolicy;
import org.cache2k.processor.EntryProcessor;
import org.cache2k.processor.MutableCacheEntry;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LocalStorage implements ThrottlerStorage {
    private Cache<String,Long> cache = Cache2kBuilder
            .of(String.class,Long.class)
            .eternal(false)
            .permitNullValues(true)
            .expireAfterWrite(32, TimeUnit.DAYS)
            .expiryPolicy(new ExpiryPolicy<String, Long>() {
                @Override
                public long calculateExpiryTime(String key, Long value, long loadTime, CacheEntry<String, Long> oldEntry) {
                    return ETERNAL;
                }
            })
            .build();

    private EntryProcessor<String, Long, Long> processor = new EntryProcessor<String, Long, Long>() {
        @Override
        public Long process(MutableCacheEntry<String, Long> e) throws Exception {
            if(e.exists() && e.getValue() != null) {
                e.setValue(e.getValue() + 1);
            } else {
                e.setValue(1L);
            }
            return e.getValue();
        }
    };


    @Override
    public Long get(String key) {
        CacheEntry<String, Long> entry = cache.getEntry(key);
        if(entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public void set(String key, Long value) {
        cache.put(key, value);
    }

    @Override
    public long incr(String key) {
        cache.putIfAbsent(key,null);
        return cache.invoke(key, processor);
    }

    @Override
    public long incr(String key, LocalDateTime expireAt) {
        cache.putIfAbsent(key,null);
        return cache.invoke(key, new EntryProcessor<String, Long, Long>() {
            @Override
            public Long process(MutableCacheEntry<String, Long> e) throws Exception {

                if(e.exists() && e.getValue() != null) {
                    e.setValue(e.getValue() + 1);
                } else {
                    e.setValue(1L);
                    LocalDateTime now = LocalDateTime.now();
                    e.setExpiry(expireAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                }
                return e.getValue();
            }
        });
    }
}
