FROM maven:3.9.0-amazoncorretto-17 AS BUILD
COPY . /mds-portal-api
WORKDIR /mds-portal-api
RUN mvn clean install -Dmaven.test.skip=true && \
    mvn clean package spring-boot:repackage -Dmaven.test.skip=true

RUN mv /mds-portal-api/target/mds-portal-*.jar /mds-portal-api/target/mds-api.jar

FROM openjdk:17-slim-buster
COPY --from=BUILD /mds-portal-api/target/ /app
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","mds-api.jar"]