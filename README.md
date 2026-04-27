# Repository Split: Claims and Learner Demo

As requested, the implementation is now split into two separate project folders so claim-processing and learner-demo changes are isolated.

## 1) claims-service
Contains only the claim payment/batch processing application.

```bash
cd claims-service
mvn clean test
mvn spring-boot:run
```

Runs on port `8080`.

## 2) learner-demo
Contains only the learner demo (Spring Boot + Angular-style frontend).

```bash
cd learner-demo
mvn clean test
mvn spring-boot:run
```

Runs on port `8081`.

---

## Why this structure
- Easier to reason about each codebase independently.
- You can now evolve/deploy claims and learner apps separately.
- Closer to “separate repository” ownership boundaries.
