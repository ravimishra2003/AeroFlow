# ✈️ AeroFlow
### 🚀 Spring Boot | REST APIs | JPA | MySQL

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![REST API](https://img.shields.io/badge/Architecture-REST-blue)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Status](https://img.shields.io/badge/Project-Production%20Ready-success)

A real-world Flight Booking backend system built using Spring Boot, focusing on transaction safety, seat availability, and clean REST API design. This project demonstrates enterprise-level backend development practices and is suitable for interviews and real-world applications.

## Why This Project Stands Out
- Real-world booking logic including seat validation and pricing
- Clean layered architecture (Controller, Service, DAO, Entity)
- Proper JPA bidirectional relationship handling
- Transaction-safe booking creation using @Transactional
- Custom exception handling with meaningful HTTP responses
- Interview-ready backend project (not just CRUD)

## Project Overview
The Flight Booking Management System is a backend application that simulates an airline booking workflow. It supports flight management, booking creation, passenger handling, payment processing, and seat availability enforcement with proper validations.

## Tech Stack
Java 17  
Spring Boot  
Spring Data JPA (Hibernate)  
RESTful APIs  
MySQL  
Postman  

## Architecture
Controller Layer → REST Endpoints  
Service Layer → Business Logic & Validation  
DAO / Repository Layer → Database Operations  
Entity Layer → Table Mapping & Relationships  

## Core Domain Models

Flight  
- Airline  
- Source and Destination  
- Departure and Arrival Time  
- Total Seats  
- Price  
- One flight can have multiple bookings  

Booking (Central Entity)  
- Booking Date  
- Booking Status  
- Flight Reference  
- Passenger List  
- Payment Details  

Passenger  
- Name  
- Age  
- Gender  
- Contact Number  
- Seat Number  
- Linked to one booking  
- @JsonIgnore used to avoid infinite JSON recursion  

Payment  
- Amount  
- Mode  
- Status  
- Payment Date  
- Exactly one payment per booking  

## Entity Relationships
Flight 1 → * Booking  
Booking 1 → * Passenger  
Booking 1 → 1 Payment  

## Business Logic Highlights
- Minimum one passenger required for booking
- Flight existence validation before booking
- Seat availability check before confirmation
- Total booking amount calculated based on passenger count
- Available seats updated after successful booking
- Automatic rollback on failure using transactional management

All critical operations are wrapped inside @Transactional to ensure data consistency.

## REST API Endpoints

Flight APIs  
- Create Flight  
- Get All Flights  
- Get Flight by ID  

Booking APIs  
- Create Booking  
- Get Booking by ID  
- Get Bookings by Flight ID  
- Get Bookings by Date  
- Get Bookings by Status  
- Update Booking Status (PATCH)  
- Delete Booking  

Passenger APIs  
- Add Passenger  
- Get Passengers by Booking  

Payment APIs  
- Get Payment by Booking  

## Partial Update Support
Booking status is updated using PATCH to modify only the required field without overwriting the entire booking object.

## Exception Handling
Custom exceptions mapped to proper HTTP status codes:

BookingNotFoundException → 404 Not Found  
FlightNotFoundException → 404 Not Found  
SeatNotAvailable → 400 Bad Request  
PassengerNotFoundException → 400 Bad Request  
PaymentNotFound → 400 Bad Request  

This ensures clean and REST-compliant error responses.

## API Testing
All APIs are tested using Postman covering:
- Successful booking flow
- Invalid flight ID
- Seat overflow scenarios
- Invalid booking status
- Missing passenger or payment details

## Database Design
flight_id is a foreign key in the Booking table  
booking_id is a foreign key in the Passenger table  
Referential integrity is maintained using JPA mappings  

## Future Enhancements
- JWT-based authentication and authorization
- Payment gateway integration
- Seat locking for high concurrency scenarios
- Swagger / OpenAPI documentation
- Role-based access control (Admin/User)

## Key Learnings
- REST API design best practices
- JPA bidirectional mapping
- Transaction management
- Custom exception handling
- Real-world backend business logic

## Conclusion
This project represents a production-ready backend system that demonstrates real-world airline booking logic, strong Spring Boot fundamentals, and clean REST API design. It is ideal for Java backend developer roles, Spring Boot interviews, and system design discussions.

If you find this project useful, consider starring the repository.
