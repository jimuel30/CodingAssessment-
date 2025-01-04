# Spring Boot Application

This is a Spring Boot application. This README provides the steps to clone, build, and run the application locally using Maven.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- [Git]
- [Java 17+]
- [Maven]

## Clone the repository 

If you received this project on a  zip file skip this step

First, clone the repository to your local machine using Git. Open your terminal and run the following command:

git clone https://github.com/jimuel30/CodingAssessment-.git




## Running on IntelliJ
Open the project folder on intellij<br>
Go to run tab and select 'Run Assignment Application'<br>


if you dont have IntelliJ you can follow the next steps



## Build the Application
Once you have cloned the repository, navigate to the project folder and build the application.


Run the following Maven command in your terminal:

mvn clean install

This will clean the project, download the necessary dependencies, and package the application into a JAR file.


## Run the Application
After building the project, you can run the application:

mvn spring-boot:run


## Testing

You can see a postman collection within this directory named "DRONE APPLICATION.postman_collection.json"
Import it on postman

On the request with <drone-serial-id> on its url you must replace this with an actual value either from the response
from registering a drone or you can check the logs this application creates 10 drones upon starting and you can copy
the serial id from there, failing to do either of this step may result to NOT FOUND response


##  Running unit tests

Execute this command to run unit tests

mvn test


