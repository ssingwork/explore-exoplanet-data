ARG BASE_JAVA_IMAGE
FROM ${BASE_JAVA_IMAGE}
WORKDIR /data
ADD target/exoplanet-data-0.0.1-SNAPSHOT.war /data/exoplanet-data.war
CMD ["java", "-jar","/data/exoplanet-data.war"]