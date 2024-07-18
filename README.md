# GitHub Repositories API

## Overview

This Spring Boot application provides an API to list all non-fork repositories of a given GitHub user, along with branch names and the last commit SHA for each branch.

## Endpoints

### Get User Repositories

- **URL:** `/api/github/repositories/{username}`
- **Method:** `GET`
- **Headers:**
  - `Accept: application/json`
- **Response:**
  - **200 OK**
    ```json
    [
      {
        "repositoryName": "repositoryName",
        "ownerLogin": "ownerLogin",
        "authorName": "Author name",
        "branches": [
          {
            "name": "branch",
            "commit": {
              "sha": "commit_sha"
            }
          }
        ]
      }
    ]
    ```
  - **Erorrs**
    ```json
    {
      "status": "error code",
      "message": "error message"
    }
    ```

## Running the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Run `mvn spring-boot:run` to start the application.

## Dependencies

- Spring Boot
- Spring Web
- Lombok
- Spring Boot DevTools

## License

This project is licensed under the MIT License.