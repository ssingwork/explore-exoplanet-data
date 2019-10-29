ARG BASE_JAVA_IMAGE
FROM ${BASE_JAVA_IMAGE}
WORKDIR /data
RUN mkdir -p /data/junks
ADD target/exoplanet-data*.war /data/exoplanet-data.war
CMD ["java", "-jar","/data/exoplanet-data.war"]
