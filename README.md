# Spring Boot Application

This is a Spring Boot application. This README provides the steps to clone, build, and run the application locally using Maven.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Git](https://git-scm.com/)
- [Java 8+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)

## Clone the repository

First, clone the repository to your local machine using Git. Open your terminal and run the following command:

git clone https://github.com/jimuel30/CodingAssessment-.git

Build the Application
Once you have cloned the repository, navigate to the project folder and build the application.

Run the following Maven command in your terminal:

mvn clean install
This will clean the project, download the necessary dependencies, and package the application into a JAR file.


Run the Application
After building the project, you can run the application:

mvn spring-boot:run
