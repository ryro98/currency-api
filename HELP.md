# REST API for communicating with Narodowy Bank Polski's public APIs
This project is a simple Spring Boot application that exposes some endpoints which take arguments and return plain 
simple data after performing certain internal operations

## Requirements
For further reference, please consider the following sections:

* Java 17
* Maven

## Installation

1. Clone the repository to your local machine.
2. In the project directory, run `mvn clean install` to build the project.
3. Run the project with `mvn spring-boot:run`.

## Usage
The REST API is available with: 
* Swagger
* app for using APIs, like Postman
* running commands

Available GET endpoints:
* GET /api/v1/avg/{currency}/{date} - provides currency's average exchange rate for the given date
* GET /api/v1/minmax/{currency}/{days} - provides the currency's max and min average value for the last n days
* GET /api/v1/bidask/{currency}/{days} - provide the currency's major difference between the buy and ask rate for the last n days

## Examples
To test endpoints, run http://localhost:8080/swagger-ui/index.html in the browser
or run commands given below.

1. GET /api/v1/avg/{currency}/{date}
* ```curl http://localhost:8080/api/v1/avg/ISK/2023-04-20``` currency: ISK, date: 2023-04-20 -> should return 0.030842
* ```curl http://localhost:8080/api/v1/avg/CZK/2023-04-24``` currency: CZK, date: 2023-04-24 -> should return 0.1968
2. GET /api/v1/minmax/{currency}/{days}
* ```curl http://localhost:8080/api/v1/minmax/ISK/100``` currency: ISK, days: 100 -> should return 
```{"min": 0.029968, "max": 0.031601}```
* ```curl http://localhost:8080/api/v1/minmax/CZK/200``` currency: CZK, days: 200 -> should return
```{"min": 0.1908,"max": 0.2017}```
3. GET /api/v1/bidask/{currency}/{days}
* ```curl http://localhost:8080/api/v1/bidask/EUR/100``` currency: EUR, days: 100 -> should return
  ```{"min": 0.0918, "max": 0.0958}```
* ```curl http://localhost:8080/api/v1/bidask/CZK/250``` currency: CZK, days: 250 -> should return
  ```{"min": 0.0036,"max": 0.004}```