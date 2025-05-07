# My API Project

## Overview
This project is a RESTful API built using Spring Boot and WebFlux, designed to handle asynchronous HTTP requests. The API allows clients to invoke external URLs with customizable headers, query parameters, and request bodies.

## Architecture
The architecture of this project is based on the following components:
- **Controller**: Handles incoming HTTP requests and delegates processing to the service layer.
- **Service**: Contains the business logic for making asynchronous HTTP calls using WebClient.
- **DTO (Data Transfer Object)**: Represents the structure of the request payload, including URL, headers, parameters, and request body.

## Technology Choices
- **Spring Boot**: A framework for building Java applications quickly and easily.
- **Spring WebFlux**: A reactive programming framework for building non-blocking applications.
- **WebClient**: A non-blocking, reactive client for making HTTP requests.
- **Reactor**: A library for building reactive applications in Java.
- **Load Testing Tools**: JMeter, k6, and Gatling for performance testing.

## Setup Instructions

### Prerequisites
- Java 11 or higher
- Maven or Gradle
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Clone the Repository

git clone https://github.com/arjun2124/HTTP-Invocation-API.git
cd  HTTP-Invocation


##API Endpoint:-
###POST /api/invoke

##Request Parameters
| Param     | Type    | Required | Description                             |
| --------- | ------- | -------- | --------------------------------------- |
| apiMethod | String  | Yes      | HTTP method: GET, POST, PUT, DELETE...  |
| timeout   | Integer | No       | Timeout in milliseconds (default: 5000) |

{
  "url": "https://example.com",
  "headerVariables": {
    "Authorization": "Bearer token"
  },
  "params": [
    { "name": "key1", "value": "value1" }
  ],
  "bodyType": "application/json",
  "requestBody": "{\"key\": \"value\"}"
}

