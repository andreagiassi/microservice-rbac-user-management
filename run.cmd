echo "Building MicroserviceDemo2..."
mvn install -DskipTests
echo "Dockerizing MicroserviceDemo2..."
docker rm micro2
docker build -t micro2 .
echo "Running the Docker container for micro2..."
docker run --name micro2 -p 8090:8090 micro2
