# Microservice Demo 2 project for Spring Boot using JPA and Dockerfile

This is a microservice project that is using Spring Boot and JPA to exposes a small set of REST apis.

The port exposed is the 8090 .

#### Database
The database schema must to be created using the simple db.sql script included one time.
The other two sql files are executed in automatic from Spring boot in order to create the db schema and to
 insert some test data.

* db_create.sql : create the empty database "reports" . 
* schema.sql : create the table/s on the database .
* data.sql : insert some test data inside the users table on start up .

#### REST apis exposed

##### Retrieve the user's list:
http://localhost:8090/users

##### Retrieve a single user by Id:
http://localhost:8090/users/1

#### Docker

To create the docker image and launch the docker container:

docker build -t micro2 . && docker run -p 8090:8090 --name micro2 micro2
