# JB Code Challenge - using the JB Umbrella as a foundation

This project contains my solution to the code challenge from Jyske Bank, where I had to make a webapp that takes text input, and returns closes matching address together with the chance of precipitation in the
next 6 hours at that location. It is based on the [Umbrella app](https://github.com/jb-roa/umbrella-app).

For the solution I used the suggested api from https://api.met.no/ in the backend, in order to receive the weather data. This data is obtained based on the latitude and longitude coordinates from the client.
For the client I opted for using the Google Places api and a wrapper module for the Google Places Autocomplete js library, in order to show autocomplete suggestions in the address search field where an address can be selected.

See instructions below on getting it up and running. Note that I experienced some issues with using docker, where it was running slow sometimes and I didnt manage to solve it in due time, 
so I recommend doing the first 2 steps instead.

## Build and run backend

With JDK11+ and Maven

```bash
cd backend
mvn package
java -jar target/weather-backend.jar
```

## Build and run frontend

With Node 10+ and NPM

```bash
cd frontend
npm install
npm start
```

## To run with Docker Compose

From project root directory.
```
docker-compose up --build
```

## To open the application

Open this URL in your preferred browser:
´´´
http://localhost:4200
´´´
