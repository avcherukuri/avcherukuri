# Claims Service (Separated Repository Layout)

This folder contains the original claim-processing Spring Boot service, isolated from the learner demo app.

## Run
```bash
cd claims-service
mvn clean test
mvn spring-boot:run
```

## Main APIs
- `POST /api/v1/claims/payments`
- `GET /api/v1/claims/payments`
- `POST /api/v1/claims/payments/batch`
