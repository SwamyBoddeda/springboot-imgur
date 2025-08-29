# Spring Boot Imgur Integration Project

A Spring Boot application that integrates with Imgur API for image management, with user authentication and registration.

## Features

- User registration and login
- image upload/view/delete via Imgur
- JWT authentication
- H2 in-memory database for development

## Prerequisites

- Java 17 or later
- Maven

## Running the Application

1. Clone or download the project.

2. Navigate to the project directory: `cd springboot-imgur`

3. Set your Imgur Client-ID:

   In `src/main/java/com/example/service/ImageService.java`, replace `YOUR_CLIENT_ID_HERE` with your actual Imgur Client-ID.

   (Get one from https://api.imgur.com/oauth2/addclient)

4. Build and run:

   ```bash
   mvn spring-boot:run
   ```

5. The application will run on http://localhost:8080

## API Endpoints


## Database

H2 console available at http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:imgurdb
username: syncb
password: syncb

## Testing

To run tests:

```bash
mvn test
```

