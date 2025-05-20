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
By default, the frontend will be available at:
http://localhost:5173

Configuration

Create a .env file in the project root and set the API URL:

VITE_API_URL=http://localhost:8080
This should point to your backend's base URL.

