# Hotel Management System

A Java Swing desktop application backed by MySQL that manages the full lifecycle of hotel operations — from guest check-in and room allocation to employee administration, driver dispatch, and checkout billing.

---

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [Data Flow](#data-flow)
- [Setup & Configuration](#setup--configuration)
- [Building & Running](#building--running)
- [Module Reference](#module-reference)

---

## Features

| Category | Capability |
|---|---|
| **Authentication** | Role-based login (Admin / Manager / Staff); session-scoped UI |
| **Room Management** | Add rooms, view all rooms, search by type/availability, update status |
| **Customer Management** | Check-in with document capture, update stay details, checkout with auto-billing |
| **Employee Management** | Add / view employees by role; Admin-only controls |
| **Driver / Pickup Service** | Register drivers, assign to customers, update availability |
| **Department Management** | View departments and budgets |
| **Feedback** | Collect and store guest ratings and comments |
| **Database Utilities** | Execute views, call stored procedures, view maintenance schedules |
| **Transaction Safety** | Optimistic concurrency (versioned rows), explicit commit/rollback |

---

## Technology Stack

| Layer | Technology |
|---|---|
| **Language** | Java 8+ |
| **GUI** | Java Swing (JFrame, JPanel, JTable, JMenuBar) |
| **Database** | MySQL 8.x |
| **Connectivity** | JDBC via `mysql-connector-java-8.0.17.jar` |
| **Table display** | `rs2xml.jar` (DbUtils – ResultSet → TableModel) |
| **Build tool** | Apache Ant (NetBeans `build.xml`) |
| **IDE** | NetBeans (project files in `nbproject/`) |

---

## Prerequisites

- Java Development Kit (JDK) 8 or later
- MySQL Server 8.x running locally (default port `3306`)
- Apache Ant (or open the project in NetBeans)

---

## Project Structure

```
Hotel-management-system/
├── src/
│   ├── hotel/management/system/          # All application source files
│   │   ├── HotelManagementSystem.java    # Splash / entry point
│   │   ├── Login.java                    # Authentication screen
│   │   ├── Dashboard.java                # Main navigation hub
│   │   ├── Reception.java                # Reception sub-menu
│   │   ├── Conn.java                     # JDBC connection & helpers
│   │   ├── TransactionManager.java       # Transaction lifecycle wrapper
│   │   ├── DatabaseUtilities.java        # Views / stored-procedure runner
│   │   ├── AddCustomer.java              # Guest check-in form
│   │   ├── CustomerInfo.java             # View all guests
│   │   ├── Checkout.java                 # Checkout & billing
│   │   ├── UpdateCheck.java              # Update guest/stay details
│   │   ├── AddRooms.java                 # Register new rooms
│   │   ├── Room.java                     # View all rooms
│   │   ├── UpdateRoom.java               # Update room status
│   │   ├── SearchRoom.java               # Filter rooms by type
│   │   ├── AddEmployee.java              # Register employee & login account
│   │   ├── EmployeeInfo.java             # View all employees
│   │   ├── ManagerInfo.java              # View manager-level employees
│   │   ├── Department.java               # View departments & budgets
│   │   ├── AddDrivers.java               # Register drivers
│   │   ├── Pickup.java                   # Driver dispatch & availability
│   │   └── FeedbackForm.java             # Guest feedback collection
│   └── icons/                            # Background images used by the UI
├── mysql-connector-java-8.0.17.jar       # JDBC driver
├── rs2xml.jar                            # ResultSet-to-TableModel helper
├── build.xml                             # Ant build script
├── manifest.mf                           # JAR manifest
└── nbproject/                            # NetBeans project metadata
```

---

## Architecture

The application follows a **3-layer desktop architecture**:

```
┌──────────────────────────────────────────────────────────────────┐
│                     PRESENTATION LAYER                           │
│  Java Swing JFrames / JPanels / JTables                          │
│                                                                  │
│  HotelManagementSystem  →  Login  →  Dashboard                   │
│  └── Reception                                                   │
│       ├── AddCustomer / CustomerInfo / Checkout / UpdateCheck    │
│       ├── Room / AddRooms / UpdateRoom / SearchRoom              │
│       ├── Pickup / AddDrivers                                    │
│       ├── Department                                             │
│       ├── FeedbackForm                                           │
│       └── DatabaseUtilities                                      │
│  └── Admin Menu                                                  │
│       ├── AddEmployee / EmployeeInfo / ManagerInfo               │
│       ├── AddRooms                                               │
│       └── AddDrivers                                             │
│  └── Feedback Menu                                               │
│       └── FeedbackForm                                           │
└──────────────────────┬───────────────────────────────────────────┘
                       │  Direct JDBC calls via Conn / TransactionManager
┌──────────────────────▼───────────────────────────────────────────┐
│                    DATA ACCESS LAYER                              │
│                                                                  │
│  Conn                    – Opens / closes JDBC Connection;       │
│                            exposes executeQuery / executeUpdate  │
│                            PreparedStatement helpers;            │
│                            calls views & stored procedures       │
│                                                                  │
│  TransactionManager      – Wraps BEGIN / COMMIT / ROLLBACK;      │
│                            optimistic locking with row versions; │
│                            logs deadlocks & failed transactions  │
│                                                                  │
│  DatabaseUtilities (UI)  – Invokes Conn.queryView() and          │
│                            Conn.executeScheduleRoomMaintenance() │
└──────────────────────┬───────────────────────────────────────────┘
                       │  JDBC (mysql-connector-java-8.0.17)
┌──────────────────────▼───────────────────────────────────────────┐
│                     DATABASE LAYER (MySQL 8)                     │
│                                                                  │
│  Database: hotelmanagementsystem2                                │
│  Tables: login, employee, department, room, customer,            │
│          driver, feedback                                        │
│  Views / Stored Procedures / Triggers (optional SQL scripts)     │
└──────────────────────────────────────────────────────────────────┘
```

### Key Design Decisions

| Decision | Detail |
|---|---|
| **No ORM** | All SQL is written inline using `PreparedStatement` to avoid injection |
| **Optimistic concurrency** | The `room` table has a `version` column; updates check the version before writing to detect conflicts |
| **Role-based UI** | `Dashboard` receives the logged-in user's role (admin / Manager / staff) and conditionally shows Admin menu items |
| **Single Conn instance per window** | Each JFrame creates its own `Conn` object (and thus its own JDBC connection) |
| **Auto-commit disabled** | `Conn` sets `autoCommit = false`; callers must call `conn.commit()` or `TransactionManager.commitTransaction()` |

---

## Database Schema

```
┌──────────────┐           ┌─────────────────────────┐
│    login     │           │        employee          │
├──────────────┤           ├─────────────────────────┤
│ username PK  │◄──────────│ username FK (optional)   │
│ password     │           │ name                     │
└──────────────┘           │ age                      │
                           │ gender                   │
                           │ job                      │
                           │ salary                   │
                           │ phone                    │
                           │ aadhar                   │
                           │ email                    │
                           └─────────────────────────┘

┌──────────────────────────────────┐
│              room                │
├──────────────────────────────────┤
│ room_number  PK                  │
│ availability  (Available/Booked) │
│ cleaning_status                  │
│ price                            │
│ bed_type                         │
│ version      (optimistic lock)   │
└─────────────┬────────────────────┘
              │ 1 : N
┌─────────────▼────────────────────────────────┐
│                   customer                    │
├──────────────────────────────────────────────┤
│ customer_id    PK (auto)                      │
│ document_type                                 │
│ document_number                               │
│ name                                          │
│ gender                                        │
│ country                                       │
│ room_number    FK → room                      │
│ check_in_time                                 │
│ deposit                                       │
└────────┬─────────────────────────────────────┘
         │ 1 : N
┌────────▼──────────────────────┐
│           feedback            │
├───────────────────────────────┤
│ feedback_id  PK (auto)        │
│ customer_id  FK → customer    │
│ room_number  FK → room        │
│ rating                        │
│ comments                      │
└───────────────────────────────┘

┌─────────────────────────────────────────┐
│                 driver                  │
├─────────────────────────────────────────┤
│ driver_id    PK (auto)                  │
│ name                                    │
│ age                                     │
│ gender                                  │
│ company                                 │
│ brand                                   │
│ available    (Available / Busy)         │
│ location                                │
│ customer_id  FK → customer (nullable)   │
└─────────────────────────────────────────┘

┌──────────────────────┐
│      department      │
├──────────────────────┤
│ department_id  PK    │
│ name                 │
│ budget               │
└──────────────────────┘
```

---

## Data Flow

### 1 — Application Startup

```
main()  [HotelManagementSystem.java]
  └── Shows animated splash screen (icons/first.jpg)
        └── User clicks "Next"
              └── new Login()
```

### 2 — Authentication

```
Login window
  ├── User enters username + password
  ├── PreparedStatement: SELECT * FROM login WHERE username=? AND password=?
  │     (Note: passwords are currently stored as plain text; a production system
  │      should hash the input and compare hashes instead)
  ├── If match found:
  │     ├── PreparedStatement: SELECT job FROM employee WHERE username=?
  │     └── new Dashboard(userRole)    ← role passed to hide/show Admin menu
  └── If no match: show error, clear password field
```

### 3 — Dashboard Navigation

```
Dashboard
  ├── Menu bar
  │     ├── "Hotel Management" → openReception()  → new Reception()
  │     ├── "Admin" (admin/Manager only)
  │     │     ├── Add Employee  → new AddEmployee(isAdmin)
  │     │     ├── Add Rooms     → new AddRooms()
  │     │     └── Add Drivers   → new AddDrivers()
  │     └── "Feedback"         → new FeedbackForm()
  └── Quick-access sidebar
        ├── Reception           → new Reception()
        └── Room Information    → new Room()
```

### 4 — Check-In Flow

```
Reception → "New Customer Form" → AddCustomer
  ├── Loads available (clean) rooms from DB:
  │     SELECT room_number, price FROM room
  │     WHERE availability='Available' AND cleaning_status='Clean'
  │     ORDER BY room_number
  ├── User fills: document type, document number, name, gender, country,
  │               room selection, check-in time, deposit
  ├── On submit:
  │     ├── Validates all fields
  │     ├── Reads current room price:
  │     │     SELECT price FROM room WHERE room_number=?
  │     ├── Inserts guest record:
  │     │     INSERT INTO customer
  │     │       (document_type, document_number, name, gender, country,
  │     │        room_number, check_in_time, deposit) VALUES (…)
  │     ├── Optimistic lock: reads version, then
  │     │     UPDATE room SET availability='Booked', version=version+1
  │     │     WHERE room_number=? AND version=?
  │     └── conn.commit()
  └── Room dropdown refreshes automatically on room selection change
```

### 5 — Checkout & Billing Flow

```
Reception → "Check Out" → Checkout
  ├── Loads all guests: SELECT customer_id, document_type, document_number
  │                     FROM customer ORDER BY document_number
  ├── User selects a guest (or searches by name/room/phone)
  ├── Details populated from: SELECT * FROM customer WHERE customer_id=?
  ├── On "Check Out":
  │     ├── Reads room price: SELECT price, bed_type FROM room WHERE room_number=?
  │     ├── Calculates total bill (deposit + nights × nightly rate)
  │     ├── Deletes feedback: DELETE FROM feedback WHERE customer_id=?
  │     ├── Deletes guest:    DELETE FROM customer WHERE customer_id=?
  │     ├── Updates room:     UPDATE room SET availability='Available',
  │     │                     version=version+1 WHERE room_number=? AND version=?
  │     └── conn.commit()
  └── Bill summary shown in a dialog before confirmation
```

### 6 — Room Management Flow

```
AddRooms
  ├── User enters: room number, availability, cleaning status, price, bed type
  ├── Duplicate check: SELECT COUNT(*) FROM room WHERE room_number=?
  ├── INSERT INTO room (room_number, availability, cleaning_status, price, bed_type) VALUES (…)
  └── conn.commit()

UpdateRoom
  ├── Loads room numbers: SELECT room_number FROM room ORDER BY room_number
  ├── Populates fields: SELECT * FROM room WHERE room_number=?
  ├── User edits availability / cleaning status / price
  ├── UPDATE room SET availability=?, cleaning_status=?, price=?,
  │   version=version+1 WHERE room_number=? AND version=?
  └── conn.commit()

SearchRoom
  ├── User selects bed type
  ├── Two queries run in parallel:
  │     SELECT * FROM room WHERE bed_type=?                      (all)
  │     SELECT * FROM room WHERE availability='Available'
  │                           AND bed_type=?                     (available only)
  └── Results shown in two side-by-side tables
```

### 7 — Employee Management Flow

```
AddEmployee
  ├── Form: name, age, gender, job, salary, phone, aadhar, email
  ├── Optional: username + password (creates login account)
  ├── Validation: email regex, 10-digit phone, 12-digit aadhar, username rules
  ├── Duplicate username check: SELECT username FROM login WHERE username=?
  ├── INSERT INTO login (username, password) VALUES (…)   ← if login provided
  ├── INSERT INTO employee (…) VALUES (…)
  └── conn.commit()

EmployeeInfo / ManagerInfo
  └── SELECT name, age, gender, job, salary, phone, email, aadhar
      FROM employee [WHERE job='Manager']
```

### 8 — Driver / Pickup Flow

```
AddDrivers
  ├── Fields: name, age, gender, company, brand, available, location
  └── INSERT INTO driver (…) VALUES (…)

Pickup
  ├── Loads all drivers filtered by brand (car type)
  ├── "Update Availability":
  │     UPDATE driver SET available=? WHERE driver_id=?
  ├── "Assign to Customer":
  │     SELECT c.customer_id, c.name, c.room_number
  │     FROM customer c WHERE c.customer_id NOT IN
  │       (SELECT customer_id FROM driver WHERE customer_id IS NOT NULL)
  │     UPDATE driver SET customer_id=?, available='Busy' WHERE driver_id=?
  └── conn.commit()
```

### 9 — Feedback Flow

```
FeedbackForm
  ├── User enters customer ID + room number
  ├── Validates: SELECT * FROM customer WHERE customer_id=? AND room_number=?
  ├── User enters rating (1–5) and comments
  ├── INSERT INTO feedback (customer_id, room_number, rating, comments) VALUES (…)
  └── conn.commit()
```

### 10 — Transaction Management

```
TransactionManager (utility class)
  ├── beginTransaction()     – sets savepoint, records transaction ID + timestamp
  ├── commitTransaction()    – calls conn.commit(), logs success
  ├── rollbackTransaction()  – calls conn.rollback(), logs reason
  ├── logDeadlock()          – records deadlock details for diagnostics
  ├── updateRoomWithVersion() – version-checked UPDATE with retry on conflict
  ├── executeInTransaction() – generic lambda wrapper (begin → fn() → commit/rollback)
  └── recoverFailedTransaction() – attempts rollback of a previously failed txn
```

---

## Setup & Configuration

### 1. Create the MySQL Database

```sql
CREATE DATABASE hotelmanagementsystem2;
USE hotelmanagementsystem2;

CREATE TABLE login (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL   -- store a hashed value (e.g. bcrypt) in production
);

CREATE TABLE employee (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    age         INT,
    gender      VARCHAR(10),
    job         VARCHAR(50),
    salary      DECIMAL(10,2),
    phone       VARCHAR(15),
    aadhar      VARCHAR(20),
    email       VARCHAR(100),
    username    VARCHAR(50),
    FOREIGN KEY (username) REFERENCES login(username)
);

CREATE TABLE department (
    department_id INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100),
    budget        DECIMAL(15,2)
);

CREATE TABLE room (
    room_number      INT PRIMARY KEY,
    availability     VARCHAR(20)  DEFAULT 'Available',
    cleaning_status  VARCHAR(20)  DEFAULT 'Clean',
    price            DECIMAL(10,2),
    bed_type         VARCHAR(30),
    version          INT          DEFAULT 0
);

CREATE TABLE customer (
    customer_id     INT AUTO_INCREMENT PRIMARY KEY,
    document_type   VARCHAR(50),
    document_number VARCHAR(50),
    name            VARCHAR(100),
    gender          VARCHAR(10),
    country         VARCHAR(100),
    room_number     INT,
    check_in_time   VARCHAR(50),
    deposit         DECIMAL(10,2),
    FOREIGN KEY (room_number) REFERENCES room(room_number)
);

CREATE TABLE driver (
    driver_id   INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100),
    age         INT,
    gender      VARCHAR(10),
    company     VARCHAR(100),
    brand       VARCHAR(50),
    available   VARCHAR(20) DEFAULT 'Available',
    location    VARCHAR(100),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE SET NULL   -- driver becomes unassigned when customer checks out
);

CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    room_number INT,
    rating      INT,
    comments    TEXT,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
        ON DELETE CASCADE,   -- feedback removed automatically on checkout
    FOREIGN KEY (room_number) REFERENCES room(room_number)
);

-- Seed the default admin account (change this password immediately after first login)
INSERT INTO login (username, password) VALUES ('admin', '<change_me>');
```

### 2. Configure Database Connection

By default the application connects to:

| Parameter | Default value |
|---|---|
| URL | `jdbc:mysql://localhost:3306/hotelmanagementsystem2` |
| Username | `root` |
| Password | `1234` |

To override, create a `config.properties` file in the working directory:

```properties
db.url=jdbc:mysql://localhost:3306/hotelmanagementsystem2
db.username=<your_mysql_user>
db.password=<your_mysql_password>
```

> **Security note:** The current codebase stores passwords as plain text and uses simple string comparison for authentication. For a production deployment, passwords should be hashed (e.g. with bcrypt) before storage, and the `login` table `password` column should only ever contain the hash.

---

## Building & Running

### Using Apache Ant

```bash
# Compile and package
ant clean jar

# Run
java -cp "dist/HotelManagementSystem.jar:mysql-connector-java-8.0.17.jar:rs2xml.jar" \
     hotel.management.system.HotelManagementSystem
```

### Using NetBeans

1. Open the project folder in NetBeans.
2. Right-click the project → **Clean and Build**.
3. Press **F6** (Run Project).

### Default Login

| Username | Password | Role |
|---|---|---|
| `admin` | *(the value you inserted during setup)* | Admin (full access) |

> **Important:** Set a strong password during database setup. Never use a trivial default in a shared or production environment.

---

## Module Reference

| Class | Responsibility |
|---|---|
| `HotelManagementSystem` | Animated splash screen; launches `Login` |
| `Login` | Authenticates against `login` table; opens `Dashboard` with role |
| `Dashboard` | Main navigation; menu bar + quick-access sidebar; role-gated menus |
| `Reception` | Sub-menu hub for day-to-day front-desk operations |
| `Conn` | JDBC connection lifecycle; `PreparedStatement` helpers; view/SP execution |
| `TransactionManager` | Explicit transaction control, optimistic locking, deadlock logging |
| `DatabaseUtilities` | UI wrapper to invoke DB views and stored procedures |
| `AddCustomer` | Guest check-in; loads available rooms; inserts customer, updates room status |
| `CustomerInfo` | Read-only table view of all current guests |
| `Checkout` | Guest checkout; calculates bill; deletes customer record; frees room |
| `UpdateCheck` | Edit guest name, room assignment, deposit while staying |
| `AddRooms` | Register new rooms with type, price, availability |
| `Room` | Read-only table view of all rooms |
| `UpdateRoom` | Modify room availability and cleaning status |
| `SearchRoom` | Filter rooms by bed type; shows all vs. available |
| `AddEmployee` | Register employee; optionally create login account |
| `EmployeeInfo` | Read-only table view of all employees |
| `ManagerInfo` | Filtered view of manager-grade employees |
| `Department` | View department names and budgets |
| `AddDrivers` | Register drivers with vehicle and location info |
| `Pickup` | Assign drivers to guests; toggle driver availability |
| `FeedbackForm` | Collect and persist guest ratings and comments |
