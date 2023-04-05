# Animal Adoption

You have been hired to develop the backend for an animal adoption app. Initially, the company will integrate with its
partners focused on [dogs](https://thedogapi.com/) and [cats](https://thecatapi.com/), but it may be extended to other
animals in the future (It's important that the service is not aware of each partner's integration).

## Pre Development

Have on your machine:

- [Java 17](https://openjdk.org/projects/jdk/17/)
- [Docker](https://www.docker.com/)
- [IDE](https://www.jetbrains.com/)

## Environment Variables

To run this project without a Dockerfile, you will need to add the following environment variables:

`SPRING_DATASOURCE_URL`

`SPRING_DATASOURCE_USERNAME`

`SPRING_DATASOURCE_PASSWORD`

## Used Stacks

**Back-end:** Java 17, Spring Boot 3.0.5

## Running locally

Clone the repository:

```bash
  git clone https://github.com/imerik1/animal-adoption
```

Enter the project directory:

```bash
  cd animal-adoption
```

Run the following command:

```bash
  docker-compose up
```

Start the server:

```bash
  curl -v http://localhost:8080/swagger-ui/index.html#/
```

## Deploy

The deployment was made on the [Digital Ocean](https://www.digitalocean.com/) platform using a private PostgreSQL
database.

## Documentação

[Postman](https://documenter.getpostman.com/view/14567989/2s93RXsASL)

[Swagger](https://sea-turtle-app-jp74n.ondigitalocean.app/swagger-ui/index.html#/)

