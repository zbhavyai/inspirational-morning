# Build Stage
FROM eclipse-temurin:21-jdk AS build
LABEL maintainer="https://zbhavyai.github.io"
LABEL repo="https://github.com/zbhavyai/inspirational-morning"
WORKDIR /opt/app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw --batch-mode dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
LABEL maintainer="https://zbhavyai.github.io"
LABEL repo="https://github.com/zbhavyai/inspirational-morning"
WORKDIR /opt/app
COPY --from=build /opt/app/target/inspirational-morning-*-runner.jar inspirational-morning-runner.jar
EXPOSE 3005
CMD ["java", "-jar", "/opt/app/inspirational-morning-runner.jar"]
