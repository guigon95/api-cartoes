
# Api Cartões

This project is a API for request cards, built with Kotlin.

## Description

The API allows you to request credit cards and receive responses indicating which cards have been approved.

## Getting Started

### Prerequisites

- Java 21 or higher

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

### API Documentation
The API documentation is available in the docs/swagger.json file. You can use Swagger UI to visualize and interact with the API.


link: http://localhost:8080/swagger-ui/index.html

![swagger.png](imagens/openapi.png)

### Coverage Report

![coverage.png](imagens/coverage.png)

### License
This project is licensed under the Apache 2.0 License - see the LICENSE file for details.