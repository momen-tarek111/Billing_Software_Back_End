# 🧾 Billing Software — Backend (Spring Boot)

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://www.oracle.com/java/)
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
| Language           | Java 17                |
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

- Java 17+
- Maven 3.8+
- MySQL 8+
- Stripe account (API keys)
- Cloudinary account (API keys)

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/billing-software-backend.git
cd billing-software-backend
```

### 2. Configure environment variables

Create an `application.properties` (or `application.yml`) file, or set the following environment variables:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/billing_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

# JWT
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000

# Stripe
stripe.api.key=your_stripe_secret_key

# Cloudinary
cloudinary.cloud_name=your_cloud_name
cloudinary.api_key=your_api_key
cloudinary.api_secret=your_api_secret
```

> ⚠️ Never commit real credentials. Use a `.env` file or environment variables and keep `application.properties` out of version control if it contains secrets (see `.gitignore` below).

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

- **Frontend (React + Vite):** `https://github.com/<your-username>/billing-software-frontend`

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

- LinkedIn: `<your-linkedin-url>`
- GitHub: `<your-github-url>`

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
