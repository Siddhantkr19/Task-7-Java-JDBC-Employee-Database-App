# ⚙️ Java MySQL Employee CRUD Application

This is a simple, console-based application built using Core Java and JDBC (Java Database Connectivity) to perform essential **CRUD (Create, Read, Update, Delete)** operations on employee data stored in a MySQL database.

---

## ✨ Features

* **1: Add Employee:** Insert a new employee record (Name, Department, Salary).
* **2: View Employees:** Display all employee records in a neatly formatted table.
* **3: Delete Employee:** Remove an employee record using their unique ID.
* **4: Update Employee:** Modify an employee's salary using their unique ID.
* **5: Exit:** Terminate the application gracefully.

---

## 🛠️ Tech Stack & Concepts

| Category | Details |
| :--- | :--- |
| **Language** | Java (1.8+) |
| **Database** | MySQL Server |
| **Connectivity** | JDBC (Java Database Connectivity) Driver |
| **Core Concepts** | `java.sql.*` (Connection, PreparedStatement, ResultSet) |
| | `java.util.Scanner` (For user input) |
| | **Try-with-resources** (For automatic resource closure) |

---

## 🚀 Getting Started

### 1. Prerequisites (What you need)

* **Java Development Kit (JDK)** installed.
* **MySQL Server** installed and running.
* **MySQL Connector/J JAR file** (The JDBC driver).

## 2. Database Setup

You must run the following SQL commands in your MySQL client (e.g., MySQL Workbench or CLI) to create the necessary database and table structure:

```sql
-- SQL Setup Script for EmployeeCrudApp

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS employee_db;

-- 2. Switch to the newly created database
USE employee_db;

-- 3. Create the main employees table
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(100),
    salary DOUBLE
);

    salary DOUBLE
);

