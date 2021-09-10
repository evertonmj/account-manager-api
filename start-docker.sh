echo "Starting application via DOCKER image"

docker build -t springio/account-manager-api .
docker run --net=host -p 8080:8080 springio/account-manager-api
