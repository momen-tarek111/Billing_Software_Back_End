# 🧾 Billing Software — Backend (Spring Boot)

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-blue?logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/Auth-JWT-black?logo=jsonwebtokens)](https://jwt.io/)
[![Stripe](https://img.shields.io/badge/Payments-Stripe-635BFF?logo=stripe)](https://stripe.com/)
[![Cloudinary](https://img.shields.io/badge/Media-Cloudinary-3448C5?logo=cloudinary)](https://cloudinary.com/)

The backend service for the **Billing Software** application — a role-based invoicing and order management system built with **Spring Boot**. It exposes secure REST APIs consumed by the [React frontend](#-related-repository).

---

## 📌 Overview

Billing Software is a full-stack point-of-sale / invoicing system designed for businesses that need fast, reliable order management. This repository contains the backend service responsible for authentication, business logic, payment processing, and data persistence.

**Frontend repository:** see the [Related Repository](#-related-repository) section below.

---

## 🌐 Live Demo

This API powers the live deployed application:

**URL:** https://billingsoftware-beta.vercel.app

**Test Account (User role):**

| Field    | Value                |
|-----------|------------------------|
| Email     | `new_user@gmail.com`  |
| Password  | `12345`                |

> ℹ️ This is a demo account with limited/sample data for evaluation purposes only. Please avoid entering real payment or personal information.

---

## ✨ Features

- 🔐 **JWT-based Authentication & Authorization** — secure login for Admin and User roles
- 👥 **Role-Based Access Control**
  - **Admin:** manage categories, manage items, manage users, create orders, print receipts, view full order history
  - **User:** create orders, print receipts, view own order history
- 🗂️ **Category & Item Management** — create categories and assign items to them, with image support
- ☁️ **Cloudinary Integration** — upload and store images for categories and items
- 💳 **Dual Payment Methods**
  - Cash payments
  - Credit/Debit card payments via **Stripe**
- 🧾 **Order & Receipt Generation** — create orders and generate printable receipts
- 📊 **Order History Tracking** — full audit trail of past orders
- 🗄️ **MySQL Database** — relational data persistence

---

## 🛠️ Tech Stack

| Layer            | Technology              |
|-------------------|--------------------------|
| Language           | Java 21                |
| Framework          | Spring Boot 3.x         |
| Security           | Spring Security + JWT   |
| Database           | MySQL                   |
| ORM                | Spring Data JPA / Hibernate |
| Payments           | Stripe API               |
| Media Storage      | Cloudinary API           |
| Build Tool         | Maven                    |

---

## 🏗️ Project Architecture

```
src/main/java/com/billingsoftware
│
├── config/          # Security, JWT, and CORS configuration
├── controller/       # REST API endpoints
├── dto/               # Data Transfer Objects
├── entity/            # JPA entities (User, Category, Item, Order, etc.)
├── repository/         # Spring Data JPA repositories
├── service/             # Business logic layer
├── security/             # JWT filters & authentication providers
└── exception/              # Global exception handling
```

---

## 🔑 Core API Modules

| Module         | Description                                          |
|-----------------|-------------------------------------------------------|
| **Auth**         | Register/login, JWT token issuance                    |
| **Users**         | Admin-only user creation & management                 |
| **Category**       | Create/update/delete categories (Admin only)         |
| **Item**             | Add/update/delete items under categories (Admin only) |
| **Order**             | Create orders, generate receipts (Admin & User)       |
| **Payment**             | Handle cash & Stripe payment processing              |
| **History**               | Retrieve order history                              |

> 💡 Full endpoint documentation (request/response samples) can be added via Swagger/OpenAPI — see [Roadmap](#-roadmap).

---

## ⚙️ Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8+
- Stripe account (API keys)
- Cloudinary account (API keys)

### 1. Clone the repository

```bash
git clone https://github.com/momen-tarek111/Billing_Software_BACK_END.git
cd Billing_Software_BACK_END
```

### 2. Configure environment variables

The application reads all sensitive configuration from **environment variables** — no secrets are hardcoded in `application.properties`. Set the following variables in your system, IDE run configuration, or hosting provider's environment settings:

| Variable                     | Description                                  |
|-------------------------------|-----------------------------------------------|
| `SPRING_DATASOURCE_URL`         | JDBC URL, e.g. `jdbc:mysql://localhost:3306/billing_db` |
| `SPRING_DATASOURCE_USERNAME`      | MySQL username                             |
| `SPRING_DATASOURCE_PASSWORD`        | MySQL password                           |
| `CLOUDINARY_CLOUD_NAME`               | Cloudinary cloud name                  |
| `CLOUDINARY_API_KEY`                    | Cloudinary API key                   |
| `CLOUDINARY_API_SECRET`                   | Cloudinary API secret              |
| `JWT_SECRET_KEY`                            | Secret key used to sign JWTs     |
| `STRIPE_SECRET_KEY`                           | Stripe secret API key          |
| `PORT`                                          | Server port (defaults to `8080`) |

`src/main/resources/application.properties`:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
server.servlet.context-path=/api/v1.0
server.port=${PORT:8080}

cloudinary.cloud-name=${CLOUDINARY_CLOUD_NAME}
cloudinary.api-key=${CLOUDINARY_API_KEY}
cloudinary.api-secret=${CLOUDINARY_API_SECRET}

jwt.secret.key=${JWT_SECRET_KEY}

stripe.secret.key=${STRIPE_SECRET_KEY}
```

> ⚠️ **Never commit real credentials.** This file is safe to commit as-is since it only references environment variable names. Set the actual values locally (e.g. in your IDE run configuration or a local `.env` loaded via your shell) and in your hosting provider's environment/secrets settings for production.

> ℹ️ Note the API base path: because of `server.servlet.context-path=/api/v1.0`, all endpoints are served under `http://localhost:8080/api/v1.0/...`.

### 3. Build and run

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## 🔒 Security Notes

- All protected endpoints require a valid **JWT** in the `Authorization: Bearer <token>` header.
- Passwords are hashed before storage.
- Role checks are enforced at both the controller and service layers.

---

## 📁 .gitignore Recommendations

```
target/
.env
application-local.properties
*.log
.idea/
*.iml
```

---

## 🔗 Related Repository

- **Frontend (React + Vite):** https://github.com/momen-tarek111/Billing_Software_FRONT_END

---

## 🗺️ Roadmap

- [ ] Add Swagger/OpenAPI documentation
- [ ] Add unit & integration tests
- [ ] Docker support
- [ ] CI/CD pipeline

---

## 👨‍💻 Author

**Eng. Momen Tarek**
Software Engineer

- LinkedIn: https://www.linkedin.com/in/momen-tarek-nagaty
- GitHub: https://github.com/momen-tarek111