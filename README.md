# Employee Management System (EMS)

A full-stack web-based Employee Management System built with Java, Spring Boot, and H2 Database. This system automates HR and employee record management operations including registration, viewing, updating, and departmental organization.

## Technology Stack

- **Language**: Java 17+
- **Framework**: Spring Boot 3.2.0
- **ORM**: Spring Data JPA
- **View Engine**: Thymeleaf
- **Styling**: Bootstrap 5
- **Build Tool**: Maven
- **Database**: H2 (In-Memory)
- **Server**: Embedded Tomcat

## Features

### 1. Login Module
- Admin login with database-stored credentials
- Session management
- Redirect to dashboard upon successful login
- Validation error messages for invalid credentials

### 2. Employee Management Module
- **Add Employees**: Create new employee records with validation
- **View Employees**: Display all employees in a responsive table
- **Edit Employees**: Update existing employee information
- **Delete Employees**: Remove employee records
- **Validation**: Empty fields and invalid data validation

### 3. Department Management Module
- **Add Departments**: Create new departments
- **View Departments**: List all departments
- **Delete Departments**: Remove department records
- **Employee Assignment**: Assign employees to departments

### 4. Dashboard
- System statistics (total employees and departments)
- Quick navigation links to all modules
- Responsive design with modern UI

## Database Schema

The application uses H2 in-memory database with the following tables:

### users
- `id` (Primary Key, Auto-generated)
- `username` (String, Unique, Not Null)
- `password` (String, Not Null)

### employees
- `emp_id` (Primary Key, Auto-generated)
- `name` (String, Not Null)
- `department` (String, Not Null)
- `designation` (String, Not Null)
- `contact` (String, 10-15 digits, Not Null)

### departments
- `dept_id` (Primary Key, Auto-generated)
- `dept_name` (String, Unique, Not Null)

## Project Structure

```
src/main/java/com/ems/
    ├── EmployeeManagementSystemApplication.java
    ├── controller/
    │   ├── LoginController.java
    │   ├── DashboardController.java
    │   ├── EmployeeController.java
    │   └── DepartmentController.java
    ├── service/
    │   ├── UserService.java
    │   ├── EmployeeService.java
    │   └── DepartmentService.java
    ├── repository/
    │   ├── UserRepository.java
    │   ├── EmployeeRepository.java
    │   └── DepartmentRepository.java
    ├── model/
    │   ├── User.java
    │   ├── Employee.java
    │   └── Department.java
    └── config/
        └── DataInitializer.java

src/main/resources/
    ├── application.properties
    └── templates/
        ├── login.html
        ├── dashboard.html
        ├── employees.html
        ├── employee-form.html
        └── departments.html
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation & Running

1. **Clone or download the project**

2. **Navigate to the project directory**
   ```bash
   cd "Employee Management system using java"
   ```

3. **Run the application using Maven**
   ```bash
   mvn spring-boot:run
   ```

   Alternatively, if you have Maven wrapper:
   ```bash
   mvnw spring-boot:run
   ```

4. **Access the application**
   - Open your web browser and navigate to: `http://localhost:8080`
   - You will be redirected to the login page

### Default Admin Credentials

- **Username**: `admin`
- **Password**: `admin123`

The default admin user is automatically created when the application starts for the first time.

## H2 Database Console

The H2 console is enabled and accessible at:
- **URL**: `http://localhost:8080/h2-console`

### H2 Console Connection Details

When accessing the H2 console, use these connection details:

- **JDBC URL**: `jdbc:h2:mem:ems_db`
- **Username**: `sa`
- **Password**: (leave empty)

## RESTful Endpoints

The application provides the following endpoints:

### Authentication
- `GET /` - Redirects to login page
- `GET /login` - Display login page
- `POST /login` - Authenticate user
- `GET /logout` - Logout and invalidate session

### Dashboard
- `GET /dashboard` - Display dashboard with statistics

### Employees
- `GET /employees` - List all employees
- `GET /employees/add` - Show add employee form
- `POST /employees/add` - Create new employee
- `GET /employees/edit/{id}` - Show edit employee form
- `POST /employees/update/{id}` - Update employee
- `GET /employees/delete/{id}` - Delete employee

### Departments
- `GET /departments` - List all departments
- `POST /departments/add` - Create new department
- `GET /departments/delete/{id}` - Delete department

## Changing Database

Currently, the application uses H2 in-memory database. To switch to PostgreSQL or MongoDB:

### PostgreSQL Configuration

1. Add PostgreSQL dependency to `pom.xml`:
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

2. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/ems_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   ```

### MongoDB Configuration

For MongoDB, you would need to:
1. Replace Spring Data JPA with Spring Data MongoDB
2. Replace `@Entity` annotations with MongoDB annotations
3. Update repository interfaces to extend `MongoRepository`

## Validation Rules

### Employee
- Name: Required, 2-100 characters
- Department: Required
- Designation: Required
- Contact: Required, 10-15 digits only

### Department
- Department Name: Required, 2-100 characters, must be unique

### User
- Username: Required, 3-50 characters, must be unique
- Password: Required, minimum 6 characters

## Build & Package

To build the project and create a JAR file:

```bash
mvn clean package
```

The JAR file will be created in the `target` directory. Run it using:

```bash
java -jar target/employee-management-system-1.0.0.jar
```

## Troubleshooting

### Port Already in Use

If port 8080 is already in use, change it in `application.properties`:

```properties
server.port=8081
```

### Database Connection Issues

Ensure the H2 database configuration in `application.properties` is correct. The H2 console should be accessible at `/h2-console`.

### Session Issues

If you experience session problems, clear your browser cookies or use an incognito/private browsing window.

## Development

### IDE Setup

This project is compatible with:
- IntelliJ IDEA
- Eclipse
- VSCode (with Java extensions)
- Cursor IDE

### Hot Reload

The project includes Spring Boot DevTools for hot reload during development. Changes to Java classes will require a restart, but Thymeleaf templates reload automatically.

## License

This project is provided as-is for educational and demonstration purposes.

## Support

For issues or questions, please refer to the Spring Boot documentation or check the application logs for detailed error messages.

---

**Note**: This application uses an in-memory H2 database, which means all data will be lost when the application stops. For production use, consider switching to a persistent database like PostgreSQL.

