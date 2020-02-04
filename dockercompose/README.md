
## 1. Program Structure
-  There are 3 folders </br>
	a. dockercompose </br>
	b. check-service </br>
	c. exchange-service </br>

## 2. How to run the Program
-  just bulid the check-service & exchange-service project with mvn clean package. </br>
-  it will build docker images for respective service. </br>
-  Then, start all the services using the docker-compose file in dockercompose folder. </br>
-  Check-service runs in 8888 port and exchange service runs in 8989 port. </br>

## 3. Architecture
-  designed these services to be decoupled and modular in nature. </br>
-  To isolate the separation of concern, i have separated the the services. </br>
-  check-service would constantly checks the 3rd party forex service and keep on inserting the currency rate into the db.</br>
		a. this service only deals with updating the db with currency exchange rate periodically and when needed for historical data. </br>
		b. the check period is configurable. Right now, it is configured to 50 seconds. </br>
		
-  exchange-service only acts as read service, that gets the latest currency rate from the db. </br>
        a. This exists separately to the read the data, so that it can individually grow. </br>
	b. for historical data, if the date range is exists in the system, then it can collects the historical data from the db itself.</br>
	   In this way, we can reduce the calls to the external api. if there are multiple records present per db in the table, then it   selects the closing record (the latest of them all).</br>
		c. if it is not in the db, then it asks the check service to collect and give it from the 3rd party API.</br>


## 4. Technology:
-  Used Spring boot 2 for developing the services.</br>
-  Docker for deploying.</br>
-  Feign client for calling the other services. Since, it provides very good abstraction and decouples from the business logic it self.</br>
-  Postgres db for storing all the currency rate.</br>
-  Swagger for documentation and testing of the API.</br>
 

## 5. TO DO:
-  Now the feign client of exchange-service directly calls the url of check-service. Though that is configurable through property.</br>
   It is wise to get that from the service discovery. </br>
-  Now the configuration for each service is stored with the service it self. Configuration management can be introduced.</br>
-  To address the scalability issue in future, we can totally make the service independent of each other by introducing Db for each service and Kafka for service collaboration as well as updating db and periodically checks for historical data as well. In this way, we can have all the data and can reduce the call to the 3rd party API.</br>
-  Circuit breaker pattern can be used specifically for historical data retrival.   


   

