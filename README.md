# Cinema

A prototype of a cinema ticket purchasing system written in Java. 

## Project Structure
The project has an N-tier structure and consists of the database layer, the DAO layer for interaction with the database, the service layer which contains the business logic, and the controller layer for HTTP request processing.  
The DAO layer is implemented with Hibernate, and DTO are used for data transfer between program tiers.  
Controllers are used to receive and respond to client requests.    

## Technologies Used

 - Java 11
 - Spring Web MVC
 - Spring Security
 - Apache Tomcat
 - MySQL RDBMS
 - Lombok
 - Log4j
 - Maven 3.1.1
 - Maven Checkstyle Plugin

## Running the Project

To run the project on your local machine, clone this project into your local folder and open the project in an IDE. 

Configure Tomcat Server and set up the MySQL DS and MySQL Workbench on your machine. 

Insert your own MySQL username and login in `db.properties` file. 

Make sure you've installed and activated the Lombok plugin for your IDE before you launch the application for the first time.

## Author

[Anastasiia Melnyk](https://github.com/AnaLanda)

