# AquaMonitor Platform

## Summary
AquaMonitor Platform is a RESTful API that registers **water sampling plans** for the monitoring
stations of rivers and reservoirs. It is built with Domain-Driven Design, a layered architecture
(domain, application, infrastructure, interfaces) and the CQRS, Resource, Assembler and Result patterns.

## Features
- `POST /api/v1/sampling-plans`: registers a sampling plan and returns `201 Created` with the created resource.
- Auto-generated `id`; `createdAt` and `updatedAt` are set by the auditable aggregate root with the same creation instant (taken from an injected `Clock`).
- Business rules:
  - `planCode` is unique (validated by the application and by the `uk_sampling_plans_plan_code` constraint in the database, translated to a domain exception): `409 Conflict`.
  - Requests are validated at the API boundary with Bean Validation and custom constraints (`@IsoLocalDate`, `@ValidSamplingWindow`, `@ValidSamplingPlanStatus`), and the domain value objects protect the same invariants.
  - `PlanCode`, `StationId`, `SamplingWindow` and `SampleVolume` value objects validate their data and throw `IllegalArgumentException` with an explanatory message: `400 Bad Request`.
  - The sampling window end date must be after its start date; `SamplingWindow.contains(LocalDate)` checks a date (both limits included).
  - `SampleVolume.addVolume(Integer)` returns a new volume with the same unit without modifying the original one.
  - `priorityLevel` must be between 1 (lowest) and 5 (highest).
  - `status` is optional: when omitted it is `PENDING`; an explicit `null` or an unknown value returns `400 Bad Request`.
- Error responses always have the body `{"message": "..."}`, localized in English (default) or Spanish (`Accept-Language: es` or `es-PE`).
- Attributes outside the contract (for example `id`, `createdAt` or `updatedAt`) are rejected with `400 Bad Request`.
- CORS support, OpenAPI documentation with Swagger UI and a snake case / pluralized physical naming strategy.

## Bounded Contexts
- **sampling**: `SamplingPlan` aggregate root and its value objects.
- **shared**: auditable aggregate root base class, `Result` pattern, error handling, i18n, clock and cross-cutting configuration.

## Technologies
- Java 25
- Spring Boot 3.5.6 (Web, Data JPA, Validation)
- MySQL (schema `aquamonitor`)
- springdoc-openapi (Swagger UI)

## Running the application
1. Start MySQL on `localhost:3306` (the `aquamonitor` schema is created automatically).
2. Adjust the credentials in `src/main/resources/application.properties` if needed.
3. Run `./mvnw spring-boot:run` (or `mvnw.cmd spring-boot:run` on Windows).
4. Open Swagger UI at http://localhost:8080/swagger-ui.html

### Example request
```http
POST /api/v1/sampling-plans
Content-Type: application/json
Accept-Language: en

{
  "planCode": "SAM-001",
  "stationId": "550e8400-e29b-41d4-a716-446655440000",
  "samplingWindowStartDate": "2026-11-21",
  "samplingWindowEndDate": "2026-11-25",
  "sampleVolumeQuantity": 10,
  "sampleVolumeUnit": "LITER",
  "priorityLevel": 3,
  "status": "PENDING",
  "samplingNotes": "Water quality sampling for river monitoring"
}
```

## Author
- José Antonio Santana Luna - Student code: u20241e281 - NRC: 7737
