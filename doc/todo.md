- HTTP Timeout (SimpleClientHttpRequestFactory does not have a suitable setConnectTimeout method)
- Entitylisteners / Auditing in native mode (https://github.com/spring-projects-experimental/spring-native/issues/869)
- Open Points from features List
                                  
                                       
---

2021-08-06 13:20:24.469  WARN 1 --- [           main] ConfigServletWebServerApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanCreationException: 

Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; 
nested exception is org.hibernate.boot.registry.classloading.spi.ClassLoadingException: Unable to load class [encryptedString]
2021-08-06 13:20:24.475  INFO 1 --- [           main] ConditionEvaluationReportLoggingListener :

Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2021-08-06 13:20:24.475 ERROR 1 --- [           main] o.s.b.d.LoggingFailureAnalysisReporter   :

***************************
APPLICATION FAILED TO START
***************************

Description:

Native reflection configuration for encryptedString is missing.

Action:

Native configuration for a class accessed reflectively is likely missing.
You can try to configure native hints in order to specify it explicitly.
See https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#native-hints for more details.

