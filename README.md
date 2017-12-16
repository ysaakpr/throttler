# PhonePe Throttler Coding

[![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid)

This is a test project for creating an application throttling framework, which can be used in distributed environment for application request throttling at multiple time unit levels, say, limit request if exceeds per second rate above 100.

With the default configuration application expect throttling.yml in your classpath; And the structure is as follows.

```
- client: client1
  limit:
    sec: 2
    min: 10
    hour: 100
    day: 1000
    month: 2000

- client: client2
  limit:
    month: 10000
  specialization:
    - type: API
      limit:
        /status:
          sec: 2
          min: 15

- client: client3
  limit:
    month: 10000
  specialization:
    - type: METHOD
      limit:
        POST:
          sec: 2
          min: 15


```

## Development and Building steps

  - Clone the repository, (Ex: further steps assume that the repository checked out at ~/throttler)
  ```
          ~$ cd ~/throttler
  throttler$ ./gradlew clean build
  throttler$ ./gradlew test  // to run the junit tests
  throttler$ ./gradlew jar   //for building the reusable jar

  ```

## Considerations

- Can use Redis as the storage implementing ThrottlerStorage and using that in the building process
- Integrations include examples to integrate with javax.Servelet Filter
- Tests have to be written to cover more code
- More stingen checks need to be added on the time related portions inorder to get better performance
- In case of distributed needs , should use a distributed, central storage(like redis) instead of the default LocalStorage

