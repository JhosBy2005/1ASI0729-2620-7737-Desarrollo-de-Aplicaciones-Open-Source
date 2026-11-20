# Daimler Truck Platform

## Summary
Daimler Truck Platform is a RESTful API that supports the management of **Authorized Workshops**
for the Daimler Truck after-sales ecosystem. It is built with Domain-Driven Design, a layered
architecture (domain, application, interfaces, infrastructure) and the CQRS, Resource and Assembler patterns.

## Features
- `POST /api/v1/workshops`: registers an authorized workshop and returns `201 Created`.
- Auto-generated `id` and `workshopId` (GUID, `DaimlerWorkshopId` value object).
- Business rules:
  - A workshop manager (first name + last name) cannot be registered twice for the same workshop legal name in the same country or region (`409 Conflict`).
  - The contact e-mail address must be valid (`400 Bad Request`).
  - The contact phone number must be a valid international number, e.g. `+51987654321` (`400 Bad Request`).
  - The 8-character `maintenanceAuthCode` is encrypted with BCrypt and stored as `encryptedAuthCode`, which is never returned by the API.
- Auditing: creation and last update dates are stored for each workshop.
- Localized error messages in English and Spanish through the `Accept-Language` header (`en`, `en-US`, `es`, `es-PE`).
- CORS support, OpenAPI documentation with Swagger UI and a snake case / pluralized physical naming strategy.

## Bounded Contexts
- **aftersales**: `AuthorizedWorkshop` aggregate root.
- **shared**: `PersonName`, `PhoneNumber` and `EmailAddress` value objects, base classes and cross-cutting configuration.

## Technologies
- Java 25
- Spring Boot 3.5.6 (Web, Data JPA, Validation)
- MySQL (schema `daimlertruck`)
- Spring Security Crypto (BCrypt)
- springdoc-openapi (Swagger UI)
- Lombok

## Running the application
1. Start MySQL on `localhost:3306` (the `daimlertruck` schema is created automatically).
2. Adjust the credentials in `src/main/resources/application.properties` if needed.
3. Run `./mvnw spring-boot:run`.
4. Open Swagger UI at http://localhost:8080/swagger-ui.html

### Example request
```json
POST /api/v1/workshops
{
  "managerFirstName": "John",
  "managerLastName": "Doe",
  "contactPhone": "+51987654321",
  "contactEmail": "john.doe@workshop.com",
  "workshopLegalName": "Lima Trucks S.A.C.",
  "city": "Lima",
  "provinceOrState": "Lima",
  "countryOrRegion": "Peru",
  "maintenanceAuthCode": "AB12CD34"
}
```

## Author
- José Antonio Santana Luna - Student code: u20241e281 - NRC: 7737
