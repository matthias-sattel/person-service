#Working
- Web, ExceptionHandler
- Lombok, Mapstruct

- Health, Prometheus
- Swagger

- Security

- JPA
- Bean Validation

- Tracing

- Auditing (not in native mode, https://github.com/spring-projects-experimental/spring-native/issues/869)
- Multi Tenancy 

#Not Working
- Flyway (https://github.com/spring-projects-experimental/spring-native/issues/778)
- Resilience (not tested)

- DurationLogger (https://github.com/spring-projects-experimental/spring-native/issues/649)

- Jaspyt Encryption
  
- Arm Builds (https://github.com/buildpacks/lifecycle/issues/435)

#build times apple silicon:
- not working

#build times github:
- console: 4m
- web: 8m
- jpa: 15m