
# Api Cartões

## Description

This project is a REST API built with Kotlin and Spring Boot. The API provides functionalities to to request credit cards and receive responses indicating which cards have been approved. It is designed to be scalable, efficient, and easy to maintain, following best practices of clean architecture, clean code principles, and modern development standards.

## Index

- ![About](https://img.icons8.com/fluency/15/000000/info.png) [About](#about)
- ![Technologies](https://img.icons8.com/fluency/15/000000/gear.png) [Technologies](#techonologies)
- ![Getting Started](https://img.icons8.com/fluency/15/000000/play.png) [Getting Started](#getting-started)
- ![Project Structure](https://img.icons8.com/fluency/15/000000/domain.png) [Project Structure](#project-structure)
- ![Tests](https://img.icons8.com/fluency/15/000000/checklist.png) [Tests](#tests)
- ![Usage Example](https://img.icons8.com/fluency/15/000000/code.png) [Usage Example](#usage-example)
- ![API Documentation](https://img.icons8.com/fluency/15/000000/book.png) [API Documentation](#api-documentation)
- ![License](https://img.icons8.com/fluency/15/000000/law.png) [License](#license)

## ![About](https://img.icons8.com/fluency/19/000000/info.png)  About

This project is a REST API built with Kotlin and Spring Boot. The API provides functionalities to to request credit cards and receive responses indicating which cards have been approved. It is designed to be scalable, efficient, and easy to maintain, following best practices of clean architecture, clean code principles, and modern development standards.

## ![Technologies](https://img.icons8.com/fluency/19/000000/gear.png) Technologies

- **Kotlin**: Modern programming language that runs on the JVM, supporting reactive and asynchronous programming with coroutines, suspend functions, etc.
- **Spring Boot**: Framework for building Java or Kotlin applications based on microservices.
- **Spring WebFlux**: For reactive applications.
- **Resilience4j**: Implementation of **Circuit Breaker**, **Rate Limiting**, **Retry**, and **Bulkhead**.
- **Micrometer**: Metrics and monitoring library.
- **JUnit 5**: Framework for unit testing.
- **Docker**: For containerizing the application.
- **GitHub Actions**: For CI/CD (optional).
- **Swagger**: For API documentation.
- **AssertJ**: For fluent assertions in tests.
- **Slf4j**: For logging.


## ![Getting Started](https://img.icons8.com/fluency/19/000000/play.png) Getting Started

### Prerequisites

#### Make sure you have the following tools installed on your machine:

- **Java 21 or higher**
- **Kotlin**
- **Maven** 
- **Docker** (if you are using containerization)

### Installing

1. Clone the repository:
    ```sh
    https://github.com/guigon95/api-cartoes.git
    cd api-cartoes
    ```

2. Install dependencies:
    ```sh
    mvn clean install
    ```

### Running the Application

To run the application, use the following command:
```sh
mvn spring-boot:run
```


The server will start on http://localhost:8080.

### Running the Application with Docker

To run the application in a Docker container, follow these steps:

1. **Build the Docker image:**

   ```sh
   docker build -t api-cartoes .
   ```

2. **Run the Docker container:**

   ```sh
   docker run -p 8080:8080 api-cartoes
   ```

This will build the Docker image and run the container, exposing the application on port 8080. You can access the application at `http://localhost:8080`.

### Docker Compose Setup
In order to quickly set up the environment, including Kafka and an external API, you can use Docker Compose. This will bring up the necessary services for running the application with Kafka and any external dependencies (e.g., Kafka broker).

1. Create a docker-compose.yml file in your project directory with the following content:

```yaml
services:
   zookeeper:
      image: confluentinc/cp-zookeeper:latest
      networks:
         - broker-kafka
      environment:
         ZOOKEEPER_CLIENT_PORT: 2181
         ZOOKEEPER_TICK_TIME: 2000

   kafka:
      image: confluentinc/cp-kafka:latest
      networks:
         - broker-kafka
      depends_on:
         - zookeeper
      ports:
         - 9092:9092
      environment:
         KAFKA_BROKER_ID: 1
         KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
         KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
         KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
         KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
         KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

   kafdrop:
      image: obsidiandynamics/kafdrop:latest
      networks:
         - broker-kafka
      depends_on:
         - kafka
      ports:
         - 19000:9000
      environment:
         KAFKA_BROKERCONNECT: kafka:29092

   api-clientes:
      image: guigon95/api-clientes:latest
      networks:
         - broker-kafka
      depends_on:
         - kafka
      ports:
         - 8082:8082
      environment:
         SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:29092
         SPRING_APPLICATION_PORT: 8082

networks:
   broker-kafka:
      driver: bridge
```

2. Start the services using Docker Compose:

```sh
docker-compose up
```
This command will bring up the Zookeeper, Kafka, kafdrop and api-clientes services. The application will be available at http://localhost:8082 and kafkadrop at 19000.

3. Stopping the services:
   To stop all the running services, use the following command:

```sh
docker-compose down
```

## ![Project Structure](https://img.icons8.com/fluency/19/000000/domain.png) Project Structure

The project structure follows the principles of Clean Architecture and is organized as follows:

```
src/
├── main/
│   ├── kotlin/
│   │   ├── com/
│   │   │   ├── guigon.api_cartoes/
│   │   │   │   ├── application/        # Use cases and ports
│   │   │   │   ├── domain/             # Domain logic (entities, rules)
│   │   │   │   ├── infrastructure/     # Implementation of external interactions (e.g., database, APIs)
│   │   │   │   ├── interfaceadapters/  # Controllers, adapters, etc.
│   └── resources/
│       ├── application.properties      # Spring Boot configurations
│       ├── logback.xml                 # Log configuration (if needed)
├── test/
│   ├── kotlin/
│   │   ├── com/
│   │   │   ├── guigon.api_cartoes/
│   │   │   │   ├── application/        # Use case tests
│   │   │   │   ├── domain/             # Business rule tests
│   │   │   │   ├── infrastructure/     # External interaction tests
│   │   │   │   ├── interfaceadapters/  # Controller and adapter tests
```

## ![Tests](https://img.icons8.com/fluency/19/000000/checklist.png) Tests

### Unit Tests
The project uses JUnit 5 for unit tests. You can run the tests with the following commands:

Using Maven:
```sh
./mvnw test
```

## ![Usage Example](https://img.icons8.com/fluency/15/000000/code.png) Usage Example

Here is an example of how to use the API to request a credit card:

1. **Request a Credit Card**

   Send a POST request to the `/cartoes` endpoint with the following JSON payload:

   ```json
   {
     "cliente": {
       "nome": "João da Silva",
       "cpf": "123.456.789-00",
       "idade": 30,
       "data_nascimento": "1990-01-01",
       "uf": "SP",
       "renda_mensal": 1000,
       "email": "joao@email.com",
       "telefone_whatsapp": "11999999999"
     }
   }
   ```

2. **Response**

   The API will respond with a JSON object indicating the request details and the offered cards:

   ```json
   {
     "numero_solicitacao": "123e4567-e89b-12d3-a456-426614174000",
     "data_solicitacao": "2025-03-28T21:48:32.896",
     "cliente": {
       "nome": "João da Silva",
       "cpf": "123.456.789-00",
       "idade": 30,
       "data_nascimento": "1990-01-01",
       "uf": "SP",
       "renda_mensal": 1000,
       "email": "joao@email.com",
       "telefone_whatsapp": "11999999999"
     },
     "cartoes_ofertados": [
       {
         "tipo_cartao": "CARTAO_SEM_ANUIDADE",
         "valor_anuidade_mensal": 0,
         "valor_limite_disponivel": 1000,
         "status": "APROVADO"
       }
     ]
   }
   ```

## ![API Documentation](https://img.icons8.com/fluency/19/000000/book.png) API Documentation
The API documentation is available in the docs/swagger.json file. You can use Swagger UI to visualize and interact with the API.


link: http://localhost:8080/swagger-ui/index.html

![swagger.png](imagens/openapi.png)


### ![License](https://img.icons8.com/fluency/19/000000/law.png) License
This project is licensed under the Apache 2.0 License - see the LICENSE file for details.