# Spring cloud reactive gateway - no CORS headers

Example project to show that the spring cloud reactive gateway does not send CORS headers.

## Test

``` sh
# build artifacts
cd gateway
./mvnw clean package
docker build -t test-cors-gateway
cd ../frontend
docker build -t test-cors-frontend
cd ..

# deploy artifacts
docker compose up -d

# [...]

# test preflight request
curl -X OPTIONS http://host.docker.internal:8081/ -v -o NUL
```