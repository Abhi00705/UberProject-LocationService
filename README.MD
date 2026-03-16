# UberProject - Location Service

## Overview

The **Location Service** is a microservice responsible for managing **driver location data** and finding **nearby drivers** for ride requests.

This service uses **Redis GEO spatial indexing** to store driver coordinates and efficiently search drivers within a specified radius.

The goal of this service is to simulate how real ride-hailing systems like Uber or Ola handle **real-time driver discovery**.

---

# Tech Stack

* **Java 17**
* **Spring Boot**
* **SQL**
* **Hibernate**
* **Spring Data Redis**
* **Redis GEO**
* **Gradle**
* **REST APIs**
* **Lombok**

---

# Features Implemented

### 1. Save Driver Location

Drivers periodically send their current latitude and longitude.
The service stores this data in Redis using **GEOADD**.

This allows the system to maintain real-time location tracking of drivers.

**Redis Structure**

Key:

```
DRIVER_GEO_OPS
```

Stored Data:

```
driverId -> latitude, longitude
```

Example Redis Command

```
GEOADD DRIVER_GEO_OPS longitude latitude driverId
```

---

### 2. Find Nearby Drivers

Passengers can request nearby drivers by providing their location.

The system performs a **radius search** using Redis GEO commands.

**Search Parameters**

* Passenger Latitude
* Passenger Longitude
* Search Radius (in kilometers)

Redis internally uses **sorted sets and geospatial indexing** to perform fast lookups.

---

# API Endpoints

## 1. Save Driver Location

### Endpoint

```
POST /drivers
```

### Request Body

```
{
  "driverId": "1",
  "latitude": 12.9716,
  "longitude": 77.5946
}
```

### Description

Stores the driver's location in Redis so that it can be used for nearby driver discovery.

---

## 2. Get Nearby Drivers

### Endpoint

```
GET /nearby/drivers
```

### Request Body

```
{
  "latitude": 12.9716,
  "longitude": 77.5946
}
```

### Response Example

```
[
  {
    "name": "driver1",
    "latitude": 12.9715,
    "longitude": 77.5942
  },
  {
    "name": "driver2",
    "latitude": 12.9721,
    "longitude": 77.5950
  }
]
```

The system searches drivers within the configured **SEARCH_RADIUS**.

---

# Core Redis GEO Concepts Used

### GEOADD

Stores the location of a driver.

### GEORADIUS

Finds drivers within a specific radius of a location.

### Coordinates Format

Redis expects coordinates in this order:

```
longitude, latitude
```

Example in Spring:

```
new Point(longitude, latitude)
```

---

# Project Structure

```
LocationService
│
├── controllers
│     LocationController
│
├── services
│     LocationService
│     SaveDriverLocationService
│
├── dto
│     SaveDriverLocationRequestDto
│     NearByDriverRequestDto
│     DriverLocationResponseDto
│
├── config
│     RedisConfig
│
└── application.properties
```

---

# How to Run the Project

### 1. Start Redis Server

If using WSL or Linux

```
redis-server
```

Verify Redis

```
redis-cli ping
```

Expected output

```
PONG
```

---

### 2. Run Spring Boot Application

```
./gradlew bootRun
```

---

# Testing Using Postman

### Save Driver Location

```
POST /drivers
```

Body

```
{
 "driverId": "1",
 "latitude": 12.9716,
 "longitude": 77.5946
}
```

---

### Find Nearby Drivers

```
GET /nearby/drivers
```

Body

```
{
 "latitude": 12.9716,
 "longitude": 77.5946
}
```

---

# Future Improvements

The following improvements will be added later:

* Return **distance of driver from passenger**
* Sort drivers by **nearest distance**
* Driver **availability status**
* Driver **heartbeat updates**
* Integration with **Booking Service**
* **Kafka based location streaming**
* **Geohash based indexing for scalability**

---

# Learning Goals

This project helped in understanding:

* Redis **Geo Spatial Indexing**
* Real-time **driver location tracking**
* Building **Spring Boot microservices**
* Efficient **location-based querying**
* Designing a backend similar to **ride-hailing systems**

---

# Author

**Abhishek Kumar**

Aspiring ** Fullstack Developer**
Currently building systems inspired by real-world architectures like **Uber and Ola**.
