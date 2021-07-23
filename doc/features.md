#Working
- Web, Health, Prometheus, ExceptionHandler
- Lombok, Mapstruct
- Security

- JPA, Bean Validation

- Tracing

- Multi Tenancy for JPA
- Auditing (not in native mode, https://github.com/spring-projects-experimental/spring-native/issues/869)               

#Not Working
- Flyway (https://github.com/spring-projects-experimental/spring-native/issues/778)
- Swagger (https://github.com/springfox/springfox/issues/3816, https://github.com/springdoc/springdoc-openapi/issues/1164)
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