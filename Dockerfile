FROM openjdk:17-jdk-alpine3.14

WORKDIR /app
COPY . .

ARG datasource_url
ENV SPRING_DATASOURCE_URL=$datasource_url

ARG datasource_username
ENV SPRING_DATASOURCE_USERNAME=$datasource_username

ARG datasource_password
ENV SPRING_DATASOURCE_PASSWORD=$datasource_password

RUN ./gradlew build

ENTRYPOINT ["java", "-jar", "/app/build/libs/Animal-Adoption-0.0.1-SNAPSHOT.jar"]