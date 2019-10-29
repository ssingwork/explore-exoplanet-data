#/bin/bash
set -e
docker run -it --rm --name my-maven-project -v "$PWD":/usr/src/mymaven -v "$HOME/.m2":/root/.m2  -w /usr/src/mymaven maven:3.6.0-jdk-8-slim mvn clean install
echo "APPS  URL :  http://localhost:9099/"
docker-compose up --build --force-recreate

