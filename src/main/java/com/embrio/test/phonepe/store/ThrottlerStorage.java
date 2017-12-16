package com.embrio.test.phonepe.store;

import java.time.LocalDateTime;

public interface ThrottlerStorage {
    //to get a stored value
    Long get(String key);
    //to set a stored value
    void set(String key, Long value);

    //to atomically increment the key value
    //if key value is null or not set initialize to zero
    //And return the incremented value  for the key
    long incr(String key);

    //In order to take care of the expiration of the values with counter behavior
    //If key not present or value is null, value will be set to 0 and will set the
    //expire to expireAt
    long incr(String key, LocalDateTime expireAt);
}
