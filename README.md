# Role Based Access Control User Management Microservice

#### Author
This project has been created in February 2020 by Andrea Giassi.

Andrea he's an italian Agile professional and Software Engineer actives in web systems and services.
Since 2002, Andrea is working in the IT market for several different companies and start ups and it has contributed
 to the success of several solutions and products.

Connect here for more info about me:
https://www.linkedin.com/in/andreagiassi/

#### Overview
Goal of this project is to offer a generic user's data management microservice.

The need to manage a user base for an online system is very frequent. This microservice can offer a good and solid starting point for managing your accounts. Thanks to the RBAC implementation, it is possible to define the roles and permissions for the specific application through REST apis and subsequently apply these roles to users.

This project lends itself very well to implement new prototypes or to create new solutions based on microservice architecture.

The project is thought using the Docker technologies with two different containers:
* Spring Boot REST apis: that exposing the services
* MySql 8.0 database: that is storing/retrieving the information

The code has been tested (more than 100 tests) using JUnit and Mockito, H2 in memory database and some standard libraries for the integration tests.

Here below the releavant features.

#### User management features

* Register a new user account
* Login with username & password
* Retrieve a single user account
* Retrieve the list of all the existing user accounts
* Update user account data (basic user data, contacts, address)
* Add or remove a role on an user account
* Delete a user account
* Define secured accounts that cannot be deleted but only modified

#### RBAC features: manages roles and permissions

* Retrieve all the permissions
* Retrieve the list of the existing roles
* Create a new role
* Retrieve a single role
* Delete a role
* Add a permission on a role
* Remove a permission on a role

#### About the Spring Boot Microservice
The microservice code is based on Java 8 and the Spring Boot 2 framework.

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

This project contains also the Postman export file micro2.postman_collection.json with some configured test calls.

#### Security, encryption and decryption of sensible data
I've followed the blog post below in order to implement an encryption and decryption method for sensible data such
 as passwords.

http://www.appsdeveloperblog.com/encrypt-user-password-example-java/

The application.properties file contains the default salt used.
