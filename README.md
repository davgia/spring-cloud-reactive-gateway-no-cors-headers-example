# Spring cloud reactive gateway - CORS testing ~~no CORS headers~~

Example project to show that the spring cloud reactive gateway ~~does not send CORS headers~~ handles CORS correctly.

## Test

``` sh
# build artifacts
cd gateway
./mvnw clean package
docker build -t test-cors-gateway .
cd ../backend
./mvnw clean package
docker build -t test-cors-backend .
cd ../frontend
docker build -t test-cors-frontend .
cd ..

# deploy artifacts
docker compose up -d

# [...]

# test preflight request
curl -X OPTIONS -H "Origin: http://host.docker.internal:8080" -H "Access-Control-Request-Headers: Origin" -H "Access-Control-Request-Method: POST" http://host.docker.internal:8081/hello -v -o /dev/null

# test allowed request
curl -X POST -H "Origin: http://host.docker.internal:8080" http://host.docker.internal:8081/hello -v -o /dev/null

# test disallowed request
curl -X PUT -H "Origin: http://host.docker.internal:8080" http://host.docker.internal:8081/hello -v -o /dev/null
```
