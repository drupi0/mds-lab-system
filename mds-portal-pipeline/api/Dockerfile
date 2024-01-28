FROM alpine/git AS INIT
ARG GIT_TOKEN=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
WORKDIR /project
RUN git clone -b master https://$GIT_TOKEN@github.com/macroscode/mds-portal-api.git


FROM maven:3.9.0-amazoncorretto-17 AS BUILD
COPY --from=INIT /project/* /mds-portal-api
WORKDIR /mds-portal-api
RUN mvn clean install -Dmaven.test.skip=true && \
    mvn clean package spring-boot:repackage -Dmaven.test.skip=true
    
RUN mv /mds-portal-api/target/mds-portal-*.jar /mds-portal-api/target/mds-api.jar

FROM openjdk:17-slim-buster
COPY --from=BUILD /mds-portal-api/target/* /app
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","mds-api.jar"]