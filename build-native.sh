#!/bin/bash
git pull
#time mvn clean spring-boot:build-image -P docker-image-native
time mvn clean spring-boot:build-image install -P docker-image-native

docker pull goafabric/person-service-native:1.0.6-SNAPSHOT && docker run --name person-service-native --rm -p50800:50800 goafabric/person-service-native:1.0.6-SNAPSHOT -Xmx64m

