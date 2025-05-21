# Smart Ledger - Personal Finance Management Application

Smart Ledger is a comprehensive personal finance management application that helps users track their income, expenses, and financial reports. The application provides a secure and user-friendly interface for managing personal finances.

## Features

- **User Authentication**: Secure JWT-based authentication with user registration, login, and role-based access control.
- **Account Management**: Create and manage different types of financial accounts (checking, savings, credit cards, etc.).
- **Transaction Tracking**: Record and categorize income and expenses.
- **Financial Reports**: Generate and view financial reports for specific time periods.
- **Role-Based Access**: Admin users can manage all data, while regular users can only access their own data.

## Technology Stack

### Backend
- **Java 21**: Core programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data access layer
- **H2 Database**: In-memory database for development
- **Liquibase**: Database schema migration
- **JWT (JSON Web Token)**: Stateless authentication
- **Lombok**: Reduces boilerplate code
- **MapStruct**: Object mapping
- **Swagger/OpenAPI**: API documentation

### Build Tools
- **Maven**: Dependency management and build automation

## Project Structure

Smart Ledger is organized as a multi-module Maven project:

- **smartledger-model**: Domain model classes
- **smartledger-dto**: Data Transfer Objects
- **smartledger-repository**: Repository interfaces for database access
- **smartledger-common**: Common utilities
- **smartledger-security**: Security configurations and JWT implementation
- **smartledger-service**: Business logic services
- **smartledger-rest**: REST controllers and API endpoints

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Building the Application
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run -pl smartledger-rest
```

The application will start on port 8080 by default.

## API Documentation

Once the application is running, you can access the Swagger UI to explore and test the API:

```
http://localhost:8080/swagger-ui/index.html
```

## Authentication

The application uses JWT for authentication. To access protected endpoints:

1. Register a new user or login with existing credentials
2. Use the returned JWT token in the Authorization header for subsequent requests:
   ```
   Authorization: Bearer <your_jwt_token>
   ```

## User Roles

- **ROLE_USER**: Regular users who can manage their own financial data
- **ROLE_ADMIN**: Administrators who can manage all users and data

## Development

### Running Tests
```bash
mvn test
```

### Database

The application uses an H2 in-memory database by default. The H2 console is available at:
```
http://localhost:8080/h2-console
```

Connection details:
- JDBC URL: `jdbc:h2:mem:smartledger`
- Username: `sa`
- Password: (empty)

## License

This project is licensed under the MIT License - see the LICENSE file for details.