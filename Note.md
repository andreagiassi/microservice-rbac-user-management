
## Setup using Docker containers
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

## Setup without Docker
You can setup and work on this project also without to consider to use Docker.

If you want to do this:
- setup a MySql on your local machine
- setup a Java 8 JDK
- change the database address on the application.properties file using localhost

Execute the microservice code using Mave:

    ./mvnw spring-boot:run
    
