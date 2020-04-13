echo "Building microservice micro-rbac-users..."
mvn install -DskipTests
echo "Dockerizing microservice micro-rbac-users..."
docker rm micro-rbac-users
docker build -t micro-rbac-users .
echo "Running container micro-rbac-users..."
docker run --name micro-rbac-users --network dev-network -p 8090:8090 micro-rbac-users
