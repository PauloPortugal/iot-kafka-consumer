#!/bin/bash
mvn -DskipTests clean package && \
docker build -t pauloportugal/iot-kafka-consumer:local . && \
docker run -ti --net=host -p 8181:8181 --rm --name iot-kafka-consumer pauloportugal/iot-kafka-consumer:local