# dependency
implementation("org.liquibase:liquibase-core")

# properties
spring.liquibase.change-log: "classpath:db/changelog/changelog-root.xml"

spring.jpa.hibernate.ddl-auto: "none"
database.provisioning.goals: "-import-demo-data"

