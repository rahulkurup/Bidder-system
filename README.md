
                                        BIDDER

  What is it?
  -----------
  This is a Web application that was developed as part of the yieldlab tech challenge.

  How to Build & Run locally
  ---------------------------
  You can run the following command to build the JAR file:  
   ./mvnw clean package 
      
  You can run the following command to run the application:    
  java -jar target/bidder-0.0.1-SNAPSHOT.war

  You can also run the application directly using the following command:  
  ./mvnw spring-boot:run  
  
  The Application would be available at: **http://localhost:8080**

  Design
  -------
  The Application is based on Spring Boot and uses Embedded Apache Tomcat server. The basic flow is:
  * The model is built based on the query params and path variable
  * The list of bidders is kept as Comma separted values in application.properties (property name: bidders.list)
  * The model is converted to JSON and a post request is send to all of the bidders
  * The response from bidder systems are converted back to JSON and parsed for required data
  * Action response is senn based on the output of response parser (Logic implemnted to handle bad responses as well)
  
  Design Constarints & improvements suggested
  -------------------------------------------
  
  * No extensive vaidation of requests and response from bidders. Asuuming that they have validation at there end and would send right request/ response.
  * No extensive Exception handling in controllers
  * Can improve the throughput using multi-thredaing 
  
   Design Advantages
   ------------------
  * Extensive use of IOC and DI.
  * Good modularity and easily testable.
  * Extensive Logging.# Bidder-system
