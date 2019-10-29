@echo off
call docker run -it --rm --name my-maven-project -v %cd%:/usr/src/mymaven -v "%userprofile%/.m2":/root/.m2 -w /usr/src/mymaven maven:3.6.0-jdk-8-slim mvn clean install
REM call mvn clean install -DskipTests

IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR building code..... "
    goto :error
)

echo "APPS  URL :  http://localhost:9099/"
call docker-compose up --build --force-recreate

IF %ERRORLEVEL% NEQ 0 (
    echo "ERROR building Docker Image..... "
    goto :error
)
:error
echo Failed with error #%errorlevel%.
EXIT /B %ERRORLEVEL%
