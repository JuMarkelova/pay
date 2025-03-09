# Local Setup

Before you proceed, ensure you have the following installed:

- Java 22
- Docker

If you haven't already cloned the repository, run the following command

  ```bash
  git clone https://github.com/JuMarkelova/pay.git
  ```

To work with a sandbox API put your actual token to [application.properties](src/main/resources/application.properties)
payment.api.token=put-your-token.
Then in home folder build the Spring Boot application using Maven

  ```bash
  mvn clean package
  ```

- ## By IDE

  To run the application, execute PayApplication from your IDE

- ## By CLI
  Run
  ```bash
  java -jar target/pay-0.0.1-SNAPSHOT.jar
  ```
  After running the application, it will be available on
  ```bash
  http://localhost:8080
  ```
  Note: Ensure that port 8080 is free and not in use by another application.
  If the port is already occupied, you can change the port by modifying the application.properties - server.port.
- ## By Docker
  In the project directory, build the Docker image by running
  ```bash
  docker build -t pay .
  ```
  Once the Docker image has been built, you can run the application inside a container with the following command
  ```bash
  docker run -p 8080:8080 pay
  ```
  After running the application, it will be available on
  ```bash
  http://localhost:8080
  ```
  Note: Ensure that port 8080 is free and not in use by another application.
  If the port is already occupied, you can change the port, you may use for instance 'docker run -p 8081:8080 pay'

# Run tests

To run tests use

  ```bash
  mvn test
  ```