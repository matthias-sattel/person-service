# docker compose
go to /src/deploy/docker and do "./stack up" or "./stack up -native"

# run jvm multi image
docker run --pull always --name person-service --rm -p50800:50800 goafabric/person-service:3.0.0-RC3

# run native image
docker run --pull always --name person-service-native --rm -p50800:50800 goafabric/person-service-native:3.0.0-RC3 -Xmx64m

# run native image arm
docker run --pull always --name person-service-native-arm64v8 --rm -p50800:50800 goafabric/person-service-native-arm64v8:3.0.0-RC3 -Xmx64m
