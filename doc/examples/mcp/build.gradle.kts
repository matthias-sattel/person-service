group = "org.goafabric"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val dockerRegistry = "goafabric"
val nativeBuilder = "dashaun/builder:20230225"
val baseImage = "ibm-semeru-runtimes:open-20.0.1_9-jre-focal@sha256:f1a10da50d02f51e79e3c9604ed078a39c19cd2711789cab7aa5d11071482a7e"

plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.23"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    
}

dependencies {
    constraints {
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        implementation("org.mapstruct:mapstruct:1.5.4.Final")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.4.Final")
        implementation("io.github.resilience4j:resilience4j-spring-boot3:2.0.2")
        implementation("net.ttddyy.observation:datasource-micrometer-spring-boot:1.0.2")
    }
}

dependencies {
    //other
    implementation(files("lib/callee-service.jar"))
    implementation(files("lib/person-service.jar"))

    //web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    //monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")

    implementation("net.ttddyy.observation:datasource-micrometer-spring-boot")

    //openapi
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

    //crosscuting
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    //adapter
    implementation("io.github.resilience4j:resilience4j-spring-boot3")

    //code generation
    implementation("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")

    //persistence
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {exclude("org.glassfish.jaxb", "jaxb-runtime")}
    implementation("com.h2database:h2")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.github.resilience4j:resilience4j-spring-boot3")}

tasks.withType<Test> {
    useJUnitPlatform()
    exclude("**/*NRIT*")
    finalizedBy("jacocoTestReport")
}
