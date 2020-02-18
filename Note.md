
## Note

The project is composed of two architectural components:
- one microservice based on Java and Spring Boot 2
- one database MySql 8.0

Follow the instructions below to setup a local docker image for the mysql8.0 database:

https://medium.com/@crmcmullen/how-to-run-mysql-in-a-docker-container-on-macos-with-persistent-local-data-58b89aec496a

After that:

- Run the database:

docker run --name mysql8.0
-v /Users/andreagiassi/dev/mysql_data/8.0:/var/lib/mysql -p 3306:3306 -d -e MYSQL_ROOT_PASSWORD=root mysql:8.0

- Retrieve the new IP's database from the docker container using the inspect command:

Ex.: docker inspect 73e5

- Change targeted IP's database in the application.properties file with the identified new one, example:

spring.datasource.url=jdbc:mysql://172.18.0.2:3306/users?useSSL=false&allowPublicKeyRetrieval=true

in my case the new database IP is 172.18.0.2 .

- Build the new micro service deploy locally with maven:

mvn install -DskipTests

- Run the micro service application:

docker run --name micro2 -p 8090:8090 micro2

- check the output calling the http://localhost:8090/users

Two additional files has been added:
* run.cmd as utility to assist during the normal development
* db.cmd as utility to pull down the database image and start it
