
## Setup notes
To compile and run the microservice code you need to setup Java 8 JDK.

Follow the instructions below to setup a local docker image for the mysql8.0 database:

https://medium.com/@crmcmullen/how-to-run-mysql-in-a-docker-container-on-macos-with-persistent-local-data-58b89aec496a

- Build the new micro service deploy file locally with maven:

mvn install -DskipTests

- Run the micro service application:

docker run --name micro-rbac-users -p 8090:8090 micro-rbac-users

Two scripts files has been created:
* run.cmd as utility to assist during the normal development
* db.cmd as utility to pull down the database image and start it

---
Setup local development network

The microservice application has been updated to support a docker dev-network and there are no needs to
 configure manually the IP address of the database target.
 
To create the dev-network is sufficient launch the Docker's command:

docker network create dev-network

To have confirmation that the new docker network has been defined run the command:

docker network ls

