
                                        BIDDER

How it works
------------

For every incoming request as described in [1], send out bid requests as described in [2] to a configurable number of bidders[5]. Responses from these bidders as described in [3] must be processed. The highest bidder wins, and payload is sent out as described in [4].

### [1] Incoming Requests

An incoming request is of the following format:

```
http://localhost:8080/[id]?[key=value,...]
```

### [2] Bid Requests

A bid request is a POST request with the following JSON body format:

```json
{
	“id”: $id,
	“attributes” : {
		“$key”: “$value”,
		…
	}
}
```

### [3] Bid Response

The response will contain details of the bid:

```json
{
	"id" : $id,
	"bid": bid,
	"content":"the string to deliver as a response"
}
```

### [4] Auction Response

The response for the auction must be the `content` property of the winning bid, with some tags that can be mentioned in the content.

For now, only `$price$` must be supported, denoting the final price of the bid.

### [5] Configuration

* bidders: a comma-separated list of URLs denoting bidder endpoints



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
