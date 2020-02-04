# Microservice Demo 2 project for Spring Boot using JPA and Dockerfile

#### Author
This small microservice project has been created in February 2020 from Andrea Giassi.

Andrea he's an italian software engineer actives in web systems and services.
Since 2002, Andrea is working in the It market for many different companies and start ups and it has contributed to the
success of several companies.

You can connect and find more info here:
https://www.linkedin.com/in/andreagiassi/

#### Overview
This project is a microservice that is using Spring Boot and JPA to exposes a small set of REST apis.

The project support the CRUD operation on an User entity on a MySql database.

The project is using the Docker technologies with two different containers:
* the Spring Boot microservice that exposes the REST apis
* the MySql database that is storing the information

#### Microservice
The microservice code is based on Java 8 and the popular Spring Boot framework.

Thanks to Spring Data the information are persisted into the database using the JPA.

Using Swagger the REST apis are also easy to manage throw the Swagger UI.

This architectural component has a specific Docker image and the services exposed
 from the microservice are available on the port 8090.

#### Database
The database schema must to be created using the simple db.sql script included one time, once the docker image will be
created.

For more information read the file Note.txt for the necessary deploy using docker.

The other two sql files are executed in automatic from Spring Boot in order to create the database schema and to
 insert some test data on the application start up.

* db_create.sql : create the empty database "reports" . 
* schema.sql : create the table/s on the database .
* data.sql : insert some test data inside the users table on start up .

#### REST apis exposed
It's possible to interact with the REST apis also with Swagger and it's comfortable UI interface.
The address to explore the REST apis using the web browser is:

http://localhost:8090/swagger-ui.html

Another alternative is to using a tool, for example Postman.
The project contains the export file micro2.postman_collection.json with some configured test calls.


