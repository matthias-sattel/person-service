import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

group = "org.goafabric"
version = "3.1.3-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val dockerRegistry = "goafabric"
val nativeBuilder = "dashaun/builder:20230225"
val baseImage = "ibm-semeru-runtimes:open-20.0.1_9-jre-focal@sha256:f1a10da50d02f51e79e3c9604ed078a39c19cd2711789cab7aa5d11071482a7e"
jacoco.toolVersion = "0.8.9"

plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.1.3"
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
	//web
	implementation("org.springframework.boot:spring-boot-starter-web")

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

	//devtools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

}

tasks.withType<Test> {
	useJUnitPlatform()
	exclude("**/*NRIT*")
	finalizedBy("jacocoTestReport")
}
tasks.jacocoTestReport { reports {csv.required.set(true) } }

jib {
	val amd64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); amd64.os = "linux"; amd64.architecture = "amd64"; val arm64 = com.google.cloud.tools.jib.gradle.PlatformParameters(); arm64.os = "linux"; arm64.architecture = "arm64"
	from.image = baseImage
	to.image = "${dockerRegistry}/${project.name}:${project.version}"
	container.jvmFlags = listOf("-Xms256m", "-Xmx256m")
	from.platforms.set(listOf(amd64, arm64))
}

/*
tasks.register("dockerImageNative") { group = "build"; dependsOn("bootBuildImage") }
tasks.named<BootBuildImage>("bootBuildImage") {
	val nativeImageName = "${dockerRegistry}/${project.name}-native" + (if (System.getProperty("os.arch").equals("aarch64")) "-arm64v8" else "") + ":${project.version}"
	builder.set(nativeBuilder)
	imageName.set(nativeImageName)
	environment.set(mapOf("BP_NATIVE_IMAGE" to "true", "BP_JVM_VERSION" to "17", "BP_NATIVE_IMAGE_BUILD_ARGUMENTS" to "-J-Xmx4500m"))
	doLast {
		exec { commandLine("docker", "run", "--rm", nativeImageName, "-check-integrity") }
		exec { commandLine("docker", "push", nativeImageName) }
	}
}
*/

val graalvmBuilderImage = "ghcr.io/graalvm/native-image-community:17.0.8"
buildscript { dependencies { classpath("com.google.cloud.tools:jib-native-image-extension-gradle:0.1.0") }}
tasks.register("dockerImageNative") {group = "build"; dependsOn("bootJar")
	doFirst {exec { commandLine(
		"docker", "run", "--rm", "--mount", "type=bind,source=${projectDir}/build,target=/build", "--entrypoint", "/bin/bash", graalvmBuilderImage, "-c", """
		mkdir -p /build/native/nativeCompile && cp /build/libs/*-SNAPSHOT.jar /build/native/nativeCompile && cd /build/native/nativeCompile && jar -xvf *.jar &&
		native-image -J-Xmx5000m -march=compatibility -H:Name=application $([[ -f META-INF/native-image/argfile ]] && echo @META-INF/native-image/argfile) -cp .:BOOT-INF/classes:$(ls -d -1 "/build/native/nativeCompile/BOOT-INF/lib/"*.* | tr "\n" ":") && /build/native/nativeCompile/application -check-integrity """
	)}}
	doLast {
		jib.from.image = "ubuntu:22.04"
		jib.to.image = "${dockerRegistry}/${project.name}-native" + (if (System.getProperty("os.arch").equals("aarch64")) "-arm64v8" else "") + ":${project.version}"
		jib.pluginExtensions { pluginExtension {
			implementation = "com.google.cloud.tools.jib.gradle.extension.nativeimage.JibNativeImageExtension"; properties = mapOf("imageName" to "application")
		}}
	}
	finalizedBy("jib")
}

graalvmNative { //https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html#configuration-options
	binaries.named("main") { quickBuild.set(true) }
}