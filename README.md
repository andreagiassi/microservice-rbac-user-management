# Microservice Demo 2 project for Spring Boot using JPA and Dockerfile

This is a microservice project that is using Spring Boot and JPA to exposes a small set of REST apis.

The database schema must to be created using the simple db.sql script included.

The port exposed is the 8090 .

#### Docker

To create the docker image and launch the docker container:

docker build -t micro2 . && docker run -p 8090:8090 --name micro2 micro2
