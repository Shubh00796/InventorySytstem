# 🛒 Inventory Management System

A real-world scalable **inventory and order management backend** designed for modern retail, warehouse, and e-commerce platforms. Built using enterprise-grade architecture, it simulates a production-ready microservice capable of handling complex business logic, high-concurrency updates, and modular extensibility.

---

## 🚀 Features

- 🔐 **JWT-Based Auth**: Role-based login (Admin, Vendor, Staff)
- 📦 **Product & Stock Management**: Add/edit/delete items with category/tags
- 📈 **Real-Time Inventory Updates**: Stock levels auto-adjust based on orders
- 📊 **Vendor-Product Mapping**: Supports multiple vendors with scoped inventories
- 🧾 **Order Fulfillment**: Validates and processes orders with atomic updates
- 🧠 **Redis Caching**: For optimized product lookups & stock status
- 🧰 **Global Exception Handling + AOP Logging**
- 🧪 **Swagger-enabled API Testing & Documentation**
- 🐳 **Docker-Ready for Seamless Deployment**

---

## 🧠 Architecture & Principles

| Layer | Description |
|-------|-------------|
| **Controller** | Exposes RESTful endpoints |
| **Service** | Business logic layer with transaction boundaries |
| **Repository** | Spring Data JPA repositories |
| **DTOs + Mappers** | MapStruct for mapping Entity <-> DTO |
| **Configuration** | JWT, Swagger, CORS, Security config |
| **Exception Handling** | Centralized with custom error response |
| **Utils** | Common logic, constants, helpers |

- ✅ **SOLID** principles
- ✅ **KISS** & **DRY** coding practices
- ✅ **Clean Architecture (SoC)** with well-defined module responsibilities

---

## 🛠️ Tech Stack

| Technology | Description |
|------------|-------------|
| Java 17 | Modern LTS version |
| Spring Boot 3 | REST API framework |
| Spring Security + JWT | Authentication & authorization |
| PostgreSQL | RDBMS for structured storage |
| Redis | In-memory caching for inventory reads |
| Lombok | For clean, boilerplate-free POJOs |
| MapStruct | Entity <-> DTO mapping |
| Docker | Containerized deployment |
| Flyway | DB version control |
| Swagger | API docs & testing |
| Logback | Structured logging |

---

## 📁 Folder Structure

```shell
inventory-system/
├── config/                   # JWT, Swagger, Redis, CORS, DB configs
├── controller/               # REST API endpoints
├── dto/                      # Request/response DTOs
├── entity/                   # JPA entities
├── mapper/                   # MapStruct mappers
├── repository/               # Spring Data repositories
├── service/                  # Business logic
├── exception/                # Global error handling
├── util/                     # Common utilities
├── resources/
│   ├── application.yml       # Environment-specific configs
│   ├── db/migration/         # Flyway migration scripts
└── Dockerfile