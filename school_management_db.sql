-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: localhost    Database: school_management_db
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_course`
--

DROP TABLE IF EXISTS `tb_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_course` (
  `code` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text,
  `max_capacity` int DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_course`
--

LOCK TABLES `tb_course` WRITE;
/*!40000 ALTER TABLE `tb_course` DISABLE KEYS */;
INSERT INTO `tb_course` VALUES ('ADMIN','ADMIN','ADMIN',0,'inactive'),('BIO101','Biology I','Introduction to biological concepts',22,'active'),('CHEM101','Chemistry I','Basic principles of chemistry',24,'active'),('CS101','Introduction to Computer Science','A foundational course covering basic computer science concepts',25,'active'),('CS201','Data Structures and Algorithms','Advanced course on data structures and algorithm design',20,'active'),('CS304','Ethics','Ethical Dilemma',30,'active'),('ENG101','English Composition','Fundamentals of academic writing and rhetoric',35,'active'),('HIST101','World History','Survey of major historical events and trends',40,'active'),('MATH101','Calculus I','Introduction to differential and integral calculus',30,'inactive'),('MATH202','Linear Algebra','Study of vector spaces and linear mappings',25,'active'),('PHYS101','Physics I','Introduction to classical mechanics',28,'active'),('PSYCH101','Introduction to Psychology','Overview of fundamental psychology concepts',35,'active');
/*!40000 ALTER TABLE `tb_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_enrollment`
--

DROP TABLE IF EXISTS `tb_enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_enrollment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `course_code` varchar(20) NOT NULL,
  `enrollment_date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_enrollment_student` (`student_id`),
  KEY `idx_enrollment_course` (`course_code`),
  CONSTRAINT `tb_enrollment_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_enrollment_ibfk_2` FOREIGN KEY (`course_code`) REFERENCES `tb_course` (`code`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_enrollment`
--

LOCK TABLES `tb_enrollment` WRITE;
/*!40000 ALTER TABLE `tb_enrollment` DISABLE KEYS */;
INSERT INTO `tb_enrollment` VALUES (1,6,'CS101','2025-01-15'),(2,6,'MATH101','2025-01-15'),(3,6,'PHYS101','2025-01-16'),(4,7,'CS101','2025-01-14'),(5,7,'CS201','2025-01-14'),(6,7,'MATH101','2025-01-14'),(10,9,'ENG101','2025-01-15'),(11,9,'HIST101','2025-01-15'),(12,9,'PSYCH101','2025-01-15'),(13,10,'BIO101','2025-01-16'),(14,10,'CHEM101','2025-01-16'),(15,10,'PSYCH101','2025-01-16'),(16,11,'CS101','2025-01-17'),(17,11,'MATH101','2025-01-17'),(18,12,'CS201','2025-01-14'),(19,12,'PHYS101','2025-01-14'),(20,13,'ENG101','2025-01-15'),(21,13,'HIST101','2025-01-15'),(22,14,'CHEM101','2025-01-17'),(23,14,'BIO101','2025-01-17'),(24,15,'PSYCH101','2025-01-16'),(25,15,'ENG101','2025-01-16');
/*!40000 ALTER TABLE `tb_enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_message`
--

DROP TABLE IF EXISTS `tb_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `recipient_id` int NOT NULL,
  `code_code` varchar(20) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `message` text,
  `timestamp` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(20) DEFAULT 'unread',
  PRIMARY KEY (`id`),
  KEY `idx_message_sender` (`sender_id`),
  KEY `idx_message_recipient` (`recipient_id`),
  KEY `idx_message_course` (`code_code`),
  CONSTRAINT `tb_message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_message_ibfk_2` FOREIGN KEY (`recipient_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_message_ibfk_3` FOREIGN KEY (`code_code`) REFERENCES `tb_course` (`code`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_message`
--

LOCK TABLES `tb_message` WRITE;
/*!40000 ALTER TABLE `tb_message` DISABLE KEYS */;
INSERT INTO `tb_message` VALUES (1,6,2,'CS101','Question about Assignment 1','Hello Professor Smith, I have a question about the first programming assignment. Could you please clarify the requirements for the third problem?','2025-02-10 14:23:15','read'),(2,7,2,'CS201','Help with Data Structures Project','Professor Smith, I am having difficulty understanding the implementation of balanced trees for our project. Could we schedule a time to discuss this?','2025-02-11 09:45:32','read'),(5,10,4,'CHEM101','Lab Experiment Clarification','Professor Williams, I am confused about the procedure for tomorrow\'s lab experiment. Could you please provide additional instructions?','2025-02-14 13:52:41','unread'),(6,2,6,'CS101','Re: Question about Assignment 1','Hello Michael, I would be happy to clarify. For problem 3, you need to implement a recursive solution that handles both the base case and the general case.','2025-02-10 15:47:10','read'),(7,2,7,'CS201','Re: Help with Data Structures Project','Jennifer, I can meet with you tomorrow during my office hours from 2-4pm. Please bring your code and specific questions.','2025-02-11 10:23:45','read'),(9,11,2,'CS101','Missed Class','Professor Smith, I missed yesterday\'s class due to illness. Could you please let me know what topics were covered?','2025-02-15 09:17:33','Read'),(10,12,2,'CS201','Project Extension Request','Professor Smith, due to unexpected circumstances, I would like to request a short extension for the upcoming project. I can provide documentation if needed.','2025-02-15 10:45:22','Read'),(12,14,4,'BIO101','Lab Partner Request','Professor Williams, I would like to request a change in lab partners for the remainder of the semester. Could we discuss this during your next office hours?','2025-02-15 15:53:07','unread'),(13,15,3,'PSYCH101','Study Group Formation','Professor Johnson, I am interested in forming a study group for our class. Would it be possible to make an announcement to help find interested classmates?','2025-02-15 16:42:19','Read'),(14,6,2,'CS101','Re: Question about Assignment 1','thanks for reaching out sure.\n--- Original ---\nHello Professor Smith, I have a question about the first programming assignment. Could you please clarify the requirements for the third problem?','2025-04-25 22:32:03','read'),(19,1,1,'ADMIN','Password Recovery','Your password is: a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','2025-04-26 09:59:55','read'),(21,2,2,'ADMIN','Password Recovery','Your password is: a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','2025-04-27 20:27:28','read'),(27,2,12,'CS201','Re: Project Extension Request','sure\n--- Original ---\nProfessor Smith, due to unexpected circumstances, I would like to request a short extension for the upcoming project. I can provide documentation if needed.','2025-04-27 21:00:15','unread'),(28,12,2,'CS201','Re: Re: Project Extension Request','much appreciated\n--- Original ---\nsure\n--- Original ---\nProfessor Smith, due to unexpected circumstances, I would like to request a short extension for the upcoming project. I can provide documentation if needed.','2025-04-27 21:02:47','unread'),(30,12,2,'CS201','I need help','Please help me on this final','2025-04-27 21:49:14','read'),(37,2,2,'ADMIN','Password Recovery','Your password is: a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','2025-04-27 22:30:01','unread'),(38,2,12,'CS201','Re: I need help','No\n\n--- Original ---\nPlease help me on this final','2025-04-27 22:31:08','read'),(39,12,2,'CS201','Re: Re: I need help','PLS\n\n--- Original ---\nNo\n\n--- Original ---\nPlease help me on this final','2025-04-27 22:31:40','unread');
/*!40000 ALTER TABLE `tb_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_teacher_courses`
--

DROP TABLE IF EXISTS `tb_teacher_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_teacher_courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NOT NULL,
  `course_code` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_teacher_courses_teacher` (`teacher_id`),
  KEY `idx_teacher_courses_course` (`course_code`),
  CONSTRAINT `tb_teacher_courses_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_teacher_courses_ibfk_2` FOREIGN KEY (`course_code`) REFERENCES `tb_course` (`code`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_teacher_courses`
--

LOCK TABLES `tb_teacher_courses` WRITE;
/*!40000 ALTER TABLE `tb_teacher_courses` DISABLE KEYS */;
INSERT INTO `tb_teacher_courses` VALUES (1,2,'CS101'),(2,2,'CS201'),(3,3,'MATH101'),(4,3,'MATH202'),(5,4,'PHYS101'),(6,4,'CHEM101'),(9,2,'BIO101'),(10,3,'PSYCH101'),(17,2,'CS304');
/*!40000 ALTER TABLE `tb_teacher_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_type` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (1,'Admin','User','admin@java.edu','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','admin'),(2,'John','Smith','jsmith@java.edu','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5','teacher'),(3,'Mary','Johnson','mjohnson@java.edu','1d8d70dddf147d2d92a634817f01b561e5a6e57741b3246f4bd19d8096dd7a46','teacher'),(4,'Robert','Williams','rwilliams@java.edu','1d8d70dddf147d2d92a634817f01b561e5a6e57741b3246f4bd19d8096dd7a46','teacher'),(6,'Michael','Davis','mdavis@java.edu','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3','student'),(7,'Jennifer','Miller','jmiller@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(9,'Linda','Moore','lmoore@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(10,'James','Taylor','jtaylor@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(11,'Elizabeth','Anderson','eanderson@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(12,'Daniel','Thomas','dthomas@java.edu','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5','student'),(13,'Barbara','Jackson','bjackson@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(14,'Joseph','White','jwhite@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student'),(15,'Susan','Harris','sharris@java.edu','2808c0ce54b18668e74717e26cd1c872e1a9ec8b0638435a13bb51d25c873e39','student');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27 22:59:18
