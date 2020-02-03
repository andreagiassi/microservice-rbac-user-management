# Microservice Demo 2 project for Spring Boot using JPA and Dockerfile

This is a microservice project that is using Spring Boot and JPA to exposes a small set of REST apis.

The micro service is connecting to another docker container with MySql 8.0 .

The services exposed from the microservice are available on the port 8090.

#### Database
The database schema must to be created using the simple db.sql script included one time, once the docker image will be
created.

Read for more information the file Note.txt for the necessary deploy using docker.

The other two sql files are executed in automatic from Spring Boot in order to create the database schema and to
 insert some test data on the application start up.

* db_create.sql : create the empty database "reports" . 
* schema.sql : create the table/s on the database .
* data.sql : insert some test data inside the users table on start up .

#### REST apis exposed

##### Retrieve the user's list:
http://localhost:8090/users

##### Retrieve a single user by Id:
http://localhost:8090/users/1

