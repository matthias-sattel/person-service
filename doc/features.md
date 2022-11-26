# Working
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
- Auditing 
- Multi Tenancy 
- Jaspyt Database Encryption

Adapter
- REST Call
- CircuitBreaker


# build times apple silicon:
- not working

# build times github:
- console: 4m
- web: 10m
- jpa: 15m

# CPU (JVM / Native)
- 10 req/s  : 25% / 25% 
- 100 req/s : 40% / 120% 
- 500 req/s : 160% / cap out at 100 req/s 

# CPU (Quarkus)
- 10 req/s  : 15%
- 100 req/s : 40%
- 500 req/s : 160% (cap out at 250 req/s)
