# Programming3_Sofiia_Hmyria

# Film Project

## Author
Sofiia Hmyria

## Domain Overview
The Film Project is a Java Spring Boot application that manages information about films, actors, and directors. 
It models the relationships between these entities, allowing users to view, add, edit, and delete records. 
The application uses Thymeleaf for server-side rendering and Bootstrap for styling.


### Entities and Relations
- Film: Represents information about a movie, including its name, release year, box office earnings, genre, directors, and actors.
- Actor: Represents an actor, including details such as name, gender, nationality, and the films they have participated in.
- Director: Represents a director with basic details.

## Profiles
- `object`: Uses in-memory data structures to store and retrieve information (for development and testing purposes).
- `jpa`: Utilizes Spring Data JPA for persistence, allowing interaction with relational databases.
- `jdbc`: Uses JDBC for database operations.

## Database
The application supports three profiles with different database configurations:
- `object` profile uses an in-memory database.
- `jpa` profile uses the H2 database for development and testing and PostgreSQL for production.
- `jdbc` profile uses plain JDBC for database operations.

## How to Run the Project
1. Choose the desired profile (`object`, `jpa`, or `jdbc`) in the `application.properties` file + database (`dev`, `prod`)
2. Configure the database connection properties accordingly. 
3. Run the application.

### Profiles Configuration
- For `object` profile: spring.profiles.active=object
- For `jpa` profile: spring.profiles.active=jpa
- For `jdbc` profile: spring.profiles.active=jdbc

### Database Configuration
- For `h2` database: Update the `application-dev.properties` with your database connection details.
- For `postgreSQL` database: Update the `application-prod.properties` with your database connection details.

