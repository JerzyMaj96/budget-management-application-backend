# Budget Management Application – Backend

REST API for personal budget management. Backend based on Spring Boot.

## Technologies

- Java 17
- Spring Boot
- Spring Security
- JPA (Hibernate)
- MySQL 
- Maven

## Requirements

- Java 17+
- Maven
- IntelliJ IDEA or other programming environment

## Run

```bash
# 1. Clone repository
git clone https://github.com/JerzyMaj96/budget-management-application.git
cd budget-management-application

# 2. Run application
./mvnw spring-boot:run
# or if you don't have the Maven wrapper:
mvn spring-boot:run

```
By default, the backend will be available at:
http://localhost:8080

Configuration

Make sure you configure your database connection in application.properties:

- spring.datasource.url=jdbc:mysql://localhost:3306/your_db
- spring.datasource.username=your_username
- spring.datasource.password=your_password
