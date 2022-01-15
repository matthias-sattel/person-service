#Working
Core
- Web, ExceptionHandler
- Lombok, Mapstruct

- Health, Prometheus
- Tracing
- OpenAPI

- Security

- DurationLogger Aspect
         
Persistence
- JPA
- Bean Validation
- Auditing (not in native mode, https://github.com/spring-projects-experimental/spring-native/issues/949)
- Multi Tenancy 
- Jaspyt Database Encryption

Adapter
- REST Call

#Not Working
- Flyway (https://github.com/spring-projects-experimental/spring-native/issues/778)
- Resilience (https://github.com/spring-projects-experimental/spring-native/issues/960)
- Spring Sleuth ConditionalProperty (https://github.com/spring-projects-experimental/spring-native/issues/764)

- Arm Builds (https://github.com/paketo-buildpacks/stacks/issues/51)

#build times apple silicon:
- not working

#build times github:
- console: 4m
- web: 10m
- jpa: 15m