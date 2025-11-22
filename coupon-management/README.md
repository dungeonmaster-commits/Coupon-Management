# Coupon Management System

A simple in-memory **Coupon Management API** built using **Java 21 + Spring Boot** as part of the assignment.
It provides APIs to create coupons, evaluate the best coupon for a user's cart, and apply a coupon with usage tracking.

---

## 🚀 Project Overview

This backend system allows:

* Admins to **create and list coupons**.
* Evaluation API to compute the **best applicable coupon** for a user + cart.
* Application API to **apply coupons and track usage per user**.
* All data stored in **InMemoryStore** (no external database).

---

## 🧰 Tech Stack

* **Java 21**
* **Spring Boot 3.x**
* **Maven**
* In-memory storage (custom `InMemoryStore`)

---

## 📦 How to Run the Project

### **Prerequisites:**

* Java 17+ (recommended Java 21)
* Maven 3+

### **Run Locally**

```bash
mvn spring-boot:run
```

OR

```bash
mvn clean package
java -jar target/*.jar
```

App runs at:

```
http://localhost:8080
```

---

## 🔑 Demo Login User (Required for Assignment)

A mandatory demo user is auto-created at startup (via SeedRunner):

```
Email: hire-me@anshumat.org
Password: HireMe@2025!
UserId: demo-001
Tier: GOLD
Country: IN
```

Use this user for `/best` and `/apply` APIs.

---

## 📘 API Endpoints

### 🧍 USER APIs

| Method | Endpoint              | Description    |
| ------ | --------------------- | -------------- |
| POST   | `/api/users`          | Create user    |
| GET    | `/api/users`          | Get all users  |
| GET    | `/api/users/{userId}` | Get user by ID |
| PUT    | `/api/users/{userId}` | Update user    |

### 🛒 COUPON ADMIN APIs

| Method | Endpoint                    | Description        |
| ------ | --------------------------- | ------------------ |
| POST   | `/api/admin/coupons`        | Create coupon      |
| GET    | `/api/admin/coupons`        | Get all coupons    |
| GET    | `/api/admin/coupons/{code}` | Get coupon by code |

### 🎯 COUPON EVALUATION APIs

| Method | Endpoint                                  | Description                       |
| ------ | ----------------------------------------- | --------------------------------- |
| POST   | `/api/coupons/best`                       | Get best coupon for a user + cart |
| POST   | `/api/coupons/apply/{couponCode}?userId=` | Apply coupon & increment usage    |

---

## 🧪 Testing With Postman

### **1. Create User**

```
POST http://localhost:8080/api/users
```

Body:

```json
{
  "userId": "u1",
  "email": "user@gmail.com",
  "password": "12345",
  "userTier": "GOLD",
  "country": "IN",
  "lifetimeSpend": 2000,
  "ordersPlaced": 3
}
```

### **2. Create Coupon**

```
POST http://localhost:8080/api/admin/coupons
```

Body:

```json
{
  "code": "WELCOME100",
  "description": "Flat 100 off",
  "discountType": "FLAT",
  "discountValue": 100,
  "startDate": "2025-01-01T00:00:00",
  "endDate": "2025-12-31T23:59:59",
  "usageLimitPerUser": 1,
  "eligibility": {
    "allowedUserTiers": ["GOLD"],
    "minLifetimeSpend": 1000,
    "minOrdersPlaced": 1,
    "firstOrderOnly": false,
    "allowedCountries": ["IN"],
    "minCartValue": 1000,
    "applicableCategories": ["ELECTRONICS"],
    "excludedCategories": ["FOOD"],
    "minItemsCount": 1
  }
}
```

### **3. Get Best Coupon**

```
POST http://localhost:8080/api/coupons/best
```

Body:

```json
{
  "userId": "demo-001",
  "cartItems": [
    { "productId": "p1", "category": "ELECTRONICS", "unitPrice": 1500, "quantity": 1 }
  ]
}
```

### **4. Apply Coupon**

```
POST http://localhost:8080/api/coupons/apply/WELCOME100?userId=demo-001
```

Body:

```json
{
  "cartItems": [
    { "productId": "p1", "category": "ELECTRONICS", "unitPrice": 1500, "quantity": 1 }
  ]
}
```

---

## 🧮 Discount Rules

* **FLAT:** discount = discountValue (capped at cart value)
* **PERCENT:** discount = (cartValue × %), capped by maxDiscountAmount

Best coupon is chosen using:

1. Highest discount amount
2. If tie → earliest endDate
3. If still tie → lexicographically smaller code

---

## 📌 Decision Notes

* duplicate coupon codes are **rejected**
* passwords stored in memory only (assignment requirement)
* no database used by design

---

## 🤖 AI Usage

This project used ChatGPT for:

* refining service layer logic
* generating README.md
* code cleanup suggestions

Prompts available on request.

---

## 📄 License

This project is part of an interview assignment — free for review and evaluation.
