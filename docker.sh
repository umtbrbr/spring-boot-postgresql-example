#!/bin/sh

#mvn clean install

echo Pulling postgre image...
docker pull postgres

echo Deleting postgre container...
docker rm postgres -f

echo Running postgre image...
docker run --name="postgres" -p 5432:5432 -d postgres

echo Building app...
docker build -t spring-boot-app:1.0.0 .

echo Deleting app...
docker rm spring-boot-app -f

echo Running app...
docker run --name="spring-boot-app" -p 8888:8888 --link postgres:postgres -d spring-boot-app:1.0.0
