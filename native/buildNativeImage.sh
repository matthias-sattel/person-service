#!/bin/bash

export GRAALVM_HOME="$(pwd)/graalvm-jdk-17"
mkdir -p /home/project/build/native/nativeCompile
cp /home/project/build/libs/*-SNAPSHOT.jar /home/project/build/native/nativeCompile
cd /home/project/build/native/nativeCompile
jar -xvf *-SNAPSHOT.jar
native-image -H:Name=$1 @META-INF/native-image/argfile -cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`