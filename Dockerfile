FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:11-oracle

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/book-service-1.0.jar /app/

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "book-service-1.0.jar"]