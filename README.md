# Role Based Access Control - User Management Microservice

#### Author
This project has been created in February 2020 by Andrea Giassi.

Andrea he's an italian Agile professional and Software Engineer actives in web systems and services.
Since 2002, Andrea is working in the IT market for several different companies and start ups and it has contributed
 to the success of several solutions and products.

About me:
https://www.linkedin.com/in/andreagiassi/

#### Overview
The need to manage a user base for an online system is very frequent.
Goal of this project is to offer a generic user's data management microservice.

This microservice can offer a good and solid starting point for managing your accounts.
Thanks to this Role Based Access Control implementation it's easy to define roles and permissions for your specific application/prototype and subsequently apply these access rules on the users.

This project lends itself very well to implement new prototypes or to create new solutions based on microservice architecture.

The solution is thought using the Docker technologies with two different containers:
* Spring Boot REST apis: that exposing the services using REST apis
* MySql 8.0 database: that is storing/retrieving the information

The code has been well tested (> 100 tests) using JUnit and Mockito, H2 in memory database and some standard libraries for the integration tests.

Here below the most relevant features exposed using REST Apis:

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
* Create a new permission
* Update an existing permission (also enabled or disable it)
* Retrieve single permission
* Delete not used permission

#### REST apis exposed
Using a browser it's possible to interact with the REST apis with Swagger:

http://localhost:8090/swagger-ui.html

![Swagger](https://github.com/andreagiassi/microservice-rbac-user-management/blob/master/src/main/resources/images/swagger.png "Swagger interface")

Another alternative is to using an external tool, for example Postman (https://www.postman.com/).

This project contains also the Postman export file with all the configured test calls:

![Swagger](https://github.com/andreagiassi/microservice-rbac-user-management/blob/master/src/main/resources/images/postman.png "Swagger interface")

## Quick Start

### Setup using Docker containers
The Docker environment is necessary in order to work with the containers and the setup depends about your Os.
Proceed the setup for the Docker environment.

The project needs two containers:
* a Java microservice
* a MySql database

To compile and run the Java project you need to install a Java 8 JDK on your local machine.

Follow the instructions below to setup a local docker image for a mysql8.0 database:

https://medium.com/@crmcmullen/how-to-run-mysql-in-a-docker-container-on-macos-with-persistent-local-data-58b89aec496a

The microservice application has been updated to support a docker dev-network and there are no needs to
 configure manually the IP address of the database target: this setup is necessary only one time.

Create the developer network:

    docker network create dev-network

Check that the network has been defined:

    docker network ls

Run the MySql container using the specific bash script:

    ./db.cmd

Execute the run bash script to compile and run the microservice container:

    ./run.cmd

Open a browser and explore the REST apis:

http://localhost:8090/swagger-ui.html

Everything should be up and running :)

### Setup without Docker
You can setup and work on this project also without to consider to use Docker.

If you want to do this:
- setup a MySql on your local machine
- setup a Java 8 JDK
- change the database address on the application.properties file using localhost

Execute the microservice code using Maven:

    ./mvnw spring-boot:run
    
Open a browser and explore the REST apis:

http://localhost:8090/swagger-ui.html

Everything should be up and running using your local MySql :)

#### Security, encryption and decryption of sensible data
I've followed the blog post below in order to implement an encryption and decryption method for sensible data such
 as passwords.

http://www.appsdeveloperblog.com/encrypt-user-password-example-java/

The application.properties file contains the default salt that will be used to encrypt the password data.
