# Microservice Demo project using Spring Boot, JPA and Docker

#### Author
This microservice project has been created in February 2020 by Andrea Giassi.

Andrea he's an italian Agile professional and Software Engineer actives in web systems and services.
Since 2002, Andrea is working in the IT market for several different companies and start ups and it has contributed
 to the success of several solutions and products.

Connect and discover more here:
https://www.linkedin.com/in/andreagiassi/

#### Overview
Goal of the project is to manage a set of users.

The microservice is using Spring Boot 2 and JPA to exposes a small set of REST apis.

The project support the basic CRUD operations on an User account and the information are stored on a
 MySql database.
 
The project is using the Docker technologies with two different containers:
* Spring Boot REST apis: that exposing the services
* MySql 8.0 database: that is storing/retrieving the information

The code has been tested using JUnit and Mockito, H2 in memory database and some standard libraries for the integration
tests.

#### Spring Boot Microservice
The microservice code is based on Java 8 and the popular Spring Boot 2 framework.

Thanks to the abstractions of Spring Data the information are persisted into the database using the JPA.
Using Swagger the REST apis are also easy to manage throw the included Swagger dependency.

This architectural component has a specific Docker image and the services exposed
 from the microservice are available on the port 8090 .

The deploy of the two architectural components is using now the docker network definition.

#### Database
The database must to be created using the simple db.sql script included one time,
 once the docker image will be created.

* db_create.sql: create the empty database "users" . 

For more information related the Docker deploy read the file Note.txt.

The other sql files are executed in automatic from Spring Boot in order to create the database schema and to
 insert some test data on the application start up.

* schema.sql: create the table/s on the database.
* data.sql: insert some test data inside the users table on start up.

#### REST apis exposed
It's possible to interact with the REST apis also with Swagger and its comfortable web interface.
The address to explore the REST apis using the web browser is:

http://localhost:8090/swagger-ui.html

Another alternative is to using an external tool, for example Postman (https://www.postman.com/).

This project contains the export file micro2.postman_collection.json with some configured test calls.

#### Security
I've followed the blog post below in order to implement an encryption and decryption for the password.

http://www.appsdeveloperblog.com/encrypt-user-password-example-java/

