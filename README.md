# Design Patterns — Java & Spring Boot + DDD + Hexagonal Architecture Examples

This repository contains practical, self-contained examples of common software design patterns implemented in **Java**, with some projects integrating **Spring Boot** to demonstrate real-world usage in enterprise-grade applications.

The goal is to show **clear, minimal, and idiomatic** implementations that follow clean architecture principles such as **Domain-Driven Design (DDD)** and **Hexagonal Architecture**, while also demonstrating how to integrate patterns into Spring Boot's dependency injection and configuration model.

---

## 📂 Repository Structure

The repository is organized by **design pattern**.  
Each folder contains one or more subprojects, ranging from pure Java ("vanilla") examples to fully wired Spring Boot applications.

| Pattern    | Project Name                                                                                                                                                                      | Technology Stack | Purpose |
|------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------|---------|
| **Decorator** | [`reservation-domain-model-decoration/vanilla`](https://github.com/gmartinez1985/design-patterns/tree/main/decorator/reservation-domain-decorator/vanilla/src/com/example/reservation)                 | Java (Vanilla) | Demonstrates the Decorator Pattern applied to the **domain model**, composing a `Reservation` with add-ons (e.g., Breakfast, Airport Pickup) without changing core logic. |
|            | [`reservation-usecase-decoration/vanilla`](https://github.com/gmartinez1985/design-patterns/tree/main/decorator/reservation-usecase-decoration/vanilla)                           | Java (Vanilla) | Demonstrates the Decorator Pattern applied to a **use case** in a clean, framework-free style. |
|            | [`reservation-usecase-decoration/spring-hexagonal-ddd`](https://github.com/gmartinez1985/design-patterns/tree/main/decorator/reservation-usecase-decoration/spring-hexagonal-ddd) | Java + Spring Boot | Same use case as above, implemented using **DDD + Hexagonal Architecture**, with decorators wired via Spring Boot configuration. |
|            | [`reservation-api-client-decorators`](https://github.com/gmartinez1985/design-patterns/tree/main/decorator/reservation-api-client-decorators/spring-hexagonal-ddd)                | Java + Spring Boot | Applies the Decorator Pattern to a **REST client** (e.g., PaymentClient), adding cross-cutting concerns like caching, retries, and circuit breaking. |
|            | [`reservation-persistance-decoration`](https://github.com/gmartinez1985/design-patterns/tree/main/decorator/reservation-persistance-decoration/spring-hexagonal-ddd)              | Java + Spring Boot | Uses the Decorator Pattern in the **persistence layer**, implementing behaviors such as caching and auditing without polluting core persistence logic. |
| **Observer** | [`vanilla`](https://github.com/gmartinez1985/design-patterns/tree/main/observer/vanilla)                                                                                          | Java (Vanilla) | Pure Java implementation of the Observer Pattern, showing publisher–subscriber mechanics without any frameworks. |
|            | [`spring-hexagonal-ddd`](https://github.com/gmartinez1985/design-patterns/tree/main/observer/spring-hexagonal-ddd)                                                                | Java + Spring Boot | Observer Pattern applied in a Spring Boot REST client scenario, leveraging Spring’s event system. |



---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.5.3** (for Spring examples)
- **Maven 3.9.4** build system

---

## 📚 How to Navigate

Each subproject is **self-contained**:
1. Navigate to the desired folder.
2. Read its `README.md` for pattern-specific details.
3. Run it independently (Spring Boot apps via `mvn spring-boot:run`, vanilla apps via `mvn exec:java` or an IDE).

---

## 🔍 Learning Approach

These examples aim to:
- **Show patterns in isolation** (vanilla examples).
- **Demonstrate patterns in realistic architectures** (Spring Boot + Hexagonal + DDD).
- Keep **business logic free from infrastructure concerns**.
- Use **configuration** for wiring behaviors instead of hardcoding.

---

## 📄 License
This repository is licensed under the MIT License.  
You are free to use, modify, and distribute these examples.

