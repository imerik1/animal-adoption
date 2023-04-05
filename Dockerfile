FROM openjdk:17-jdk-alpine3.14

WORKDIR /app
COPY . .

RUN chmod +x gradlew
RUN ./gradlew build

ENTRYPOINT ["java", "-jar", "/app/build/libs/Animal-Adoption-0.0.1-SNAPSHOT.jar"]