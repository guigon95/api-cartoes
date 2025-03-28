FROM openjdk:21-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src/ ./src/
RUN chmod +x mvnw
RUN ./mvnw clean install
CMD ["java", "-jar", "/app/target/api-cartoes-1.0.0.jar"]