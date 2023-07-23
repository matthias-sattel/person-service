docker build -t native-image-builder ./native

gradle clean bootJar

docker run --rm --name person-service-builder --mount type=bind,source=$(pwd)/build,target=/home/project/build \
native-image-builder /bin/bash -c "/home/project/buildNativeImage.sh myapplication"



docker container run -d --name person-service-builder --mount type=bind,source=$(pwd)/build,target=/home/project/build \
native-image-builder tail -f /dev/null

docker exec person-service-builder /bin/bash -c "/home/project/buildNativeImage.sh person-service"



gradle jibNativeImage -PnativeImageVersionType="SNAPSHOT"