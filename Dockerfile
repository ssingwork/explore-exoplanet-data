ARG BASE_JAVA_IMAGE
FROM ${BASE_JAVA_IMAGE}
WORKDIR /data
VOLUME /data/junks
ADD target/exoplanet-data*.war /data/exoplanet-data.war
EXPOSE 9099
CMD ["java", "-jar","/data/exoplanet-data.war"]
