<img width="999" alt="Screenshot 2025-04-30 at 2 28 35 PM" src="https://github.com/user-attachments/assets/a31badb1-4209-4fcf-a2f6-0dd030d21e41" />

# University Management System
Created by Daniel Huynh

## Project Overview
The School Management System is a comprehensive Java Swing application designed to manage educational institutions. This system provides separate interfaces for administrators, teachers, and students, allowing for efficient management of user accounts, courses, enrollments, and communication between users.

## Features
### User Authentication
- Secure login system with password hashing
- Role-based access control (Admin, Teacher, Student)
- Password recovery mechanism

### Administrator Features
- User Directory Management
  - Create, update, and delete user accounts
  - Assign roles (teacher or student)
  - Search users by email
- Course Management
  - Create, update, and delete courses
  - Set course capacity and status
- Teacher-Course Assignment
  - Assign teachers to specific courses
  - Manage teaching responsibilities

### Teacher Features
- View assigned courses
- View students enrolled in assigned courses
- Send messages to students

### Student Features
- View available courses for enrollment
- Enroll in available courses
- Withdraw from enrolled courses
- View currently enrolled courses

### Messaging System
- Send and receive messages between users
- Course-specific messaging
- Message status tracking (read/unread)

### Profile Management
- View user profile information
- Change password functionality

## Technical Implementation
### MVC Architecture
The application follows the Model-View-Controller (MVC) architectural pattern:
- **Models**: Represent data structures (User, Course, Enrollment, Message)
- **Views**: Handle user interface components
- **Controllers**: Manage application logic and user interactions

### Security
- Password encryption using SHA-256 hashing
- Input validation to prevent malicious entries
- Email validation for proper format

### Database Management
- SQL-based data storage
- Dedicated Data Access Objects (DAOs) for each entity
- Transaction management for data integrity

## Project Structure
- **view**: Contains all UI components
  - Admin, Teacher, Student, Auth, Common, Message, Profile views
- **controller**: Contains logic for handling user interactions
  - Separate controllers for each view
- **model**: Contains data structures
  - User, Course, Message, Enrollment, AssignedCourse
- **dao**: Contains database access objects
  - DirectoryDAO, CourseDAO, EnrollmentDAO, MessageDAO, TeacherCourseDAO
- **my_util**: Contains utility classes
  - Security, EmailUtil, DatabaseUtil, Verification

## Screenshots
### Login Screen
<img width="641" alt="Screenshot 2025-04-30 at 2 27 53 PM" src="https://github.com/user-attachments/assets/5e711793-7b38-4329-bee6-f0879746271e" />

### User Message Dashboard
<img width="997" alt="Screenshot 2025-04-30 at 2 29 51 PM" src="https://github.com/user-attachments/assets/4a052a5e-319c-4285-b84a-3d0e7cc06124" />

### Student Dashboard
<img width="996" alt="Screenshot 2025-04-30 at 2 43 20 PM" src="https://github.com/user-attachments/assets/0cfb9367-4835-4fcc-be74-363924b352e9" />

### Teacher Dashboard
<img width="996" alt="Screenshot 2025-04-30 at 2 44 13 PM" src="https://github.com/user-attachments/assets/1ad15bc8-a7aa-47dc-87e6-28b23bdfac24" />


## Installation and Setup
1. Ensure Java JDK 8 or higher is installed
2. Set up a MySQL database and run the included SQL script
3. Configure database connection in DatabaseUtil.java
4. Compile the project:
   ```
   javac -d bin src/**/*.java
   ```
5. Run the application:
   ```
   java -cp bin Main
   ```



