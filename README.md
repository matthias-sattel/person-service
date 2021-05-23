#run native image
docker pull goafabric/person-service-native:1.0.0-SNAPSHOT && docker run --name person-service-native --rm -p50800:50800 goafabric/person-service-native:1.0.0-SNAPSHOT -Xmx64m