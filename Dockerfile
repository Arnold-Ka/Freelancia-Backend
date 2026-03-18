FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src


RUN mvn clean package -DskipTests -B


FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=build /app/target/freelancia*.jar app.jar


ENV JAVA_OPTS="-Xmx512m -Xms256m"

EXPOSE 33726

USER appuser

ENTRYPOINT [ "sh", "-c", "java -jar app.jar" ]