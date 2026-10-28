# testdrive-app

A web application for booking car test drives. Customers register, submit a test drive request with their driver's license attached, and track its status. Administrators review requests and approve or reject them. Approved customers get an email confirmation and a reminder the day before the test drive.

This application was developed as part of my internship at JSC Allur. It is a learning and demonstration project and is not intended for production use as-is.

Features

For customers
Registration and login (email + password)
Test drive request form: name, phone, date, location, driver's license upload (JPG, PNG or PDF, up to 10 MB)
"My bookings" page showing the status of each request (`PENDING`, `APPROVED`, `REJECTED`)

For administrators
Dashboard listing all requests
View the uploaded driver's license in the browser
Approve or reject requests

Automatic emails
Approval email sent when an admin approves a request
Reminder email sent every day at 09:00 for approved test drives scheduled for the next day

#Tech Stack

| Language | Java 17 |
| Framework | Spring Boot 3.2.3 (Web, Data JPA, Security, Validation, Mail) |
| Templates | Thymeleaf + Thymeleaf Spring Security extras |
| Database | H2 (file-based) |
| UI | Bootstrap 5 |
| Build | Maven |
| Other | Lombok, BCrypt password hashing |

Project Structure (AI was used to demonstrate the structure)

src/main/java/com/allur/testdrive/
├── config/        Security setup, password encoder, default admin seeding
├── controller/    Auth, Booking (customer) and Admin controllers
├── dto/           Registration form DTO with validation
├── model/         User, Booking, Role (USER/ADMIN), BookingStatus
├── repository/    Spring Data JPA repositories
└── service/       Booking and user logic, email sending, reminder scheduler
src/main/resources/
├── templates/     Thymeleaf pages (auth, booking, admin)
├── static/css/    Styles
└── application.properties

Prerequisites
- JDK 17+
- Maven 3.6+

The app starts at http://localhost:8081.

Default admin account

On first start an admin user is created automatically:

| Email | Password |
|-------|----------|
| `admin@allur.kz` | `admin123` |

Configuration

Settings live in `src/main/resources/application.properties`.

| `server.port` | HTTP port | `8081` |
| `spring.datasource.url` | H2 database file | `jdbc:h2:file:./testdrivedb` |
| `spring.mail.*` | SMTP settings for emails | Gmail SMTP, port 587 |

Email setup

Emails are sent through Gmail SMTP. 

A Gmail [app password](https://support.google.com/accounts/answer/185833) is required if 2-step verification is enabled. If email sending fails, the error is logged and the application keeps working.

Routes

| `/login`, `/register` | Public | Authentication pages |
| `/booking/new` | Logged-in user | Submit a test drive request |
| `/booking/my` | Logged-in user | View own requests |
| `/admin/dashboard` | Admin | Review all requests |
| `/admin/booking/{id}/approve` | Admin | Approve a request (POST) |
| `/admin/booking/{id}/reject` | Admin | Reject a request (POST) |
| `/admin/booking/{id}/license` | Admin | View the uploaded license |
| `/h2-console` | Enabled in dev | H2 database console |

Data Model

User: email (unique), hashed password, first name, last name, role (`USER` / `ADMIN`), enabled flag
Booking: user, contact details, test drive date, location, license file (stored as a BLOB), status, creation time, reminder-sent flag

Notes

The user interface and email texts are in Russian.
Available test drive locations are currently hardcoded in `templates/booking/form.html` (Astana and Almaty branches).
The H2 console is enabled for development. Disable it (`spring.h2.console.enabled=false`) in any shared or production environment.

## License

No license specified. All rights reserved by the author and the company unless stated otherwise.
