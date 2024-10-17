## Run the Application

To run the application, run the following command in a terminal window from the root directory:

    ./gradlew bootRun

## Test service based app
Now run the service with curl (in a separate terminal window), by running the following
command (shown with its output):

    $ curl http://localhost:8080/

## Upload CSV file 
* Open http://localhost:8080/ URL in a web browser
* Click on [Task1: Import and process CSV file in async job](http://localhost:8080/import)
* Browse for a [sample](samples/geoclasses.csv) and then click "Upload File"
* Track the status of the file being processed under http://localhost:8080/status/{fileName}

## Exercise 1
The implementation can be found by the link [src/main/java/com/example/task1](src/main/java/com/example/task1)

### Requirements fullfilment:
1) It should be a service-based app
    * web app with REST API
2) File should be parsed in asynchronous way, result should be stored in DB
    * [asynchronous task](https://github.com/yegor86/spring-boot-app/blob/main/src/main/java/com/example/task1/service/FileProcessingService.java#L36)
    * Spring Data and [a model](https://github.com/yegor86/spring-boot-app/blob/main/src/main/java/com/example/task1/model/GeoClass.java)
3) Database config and import folder should be configurable, e.g.: json config file
    * [External Postgres DB](https://github.com/yegor86/spring-boot-app/blob/main/src/main/resources/application.properties#L8-L12)
    * A folder is selected via HTML submit form when opening http://localhost:8080/ URL 
4) Logs should be stored in files
    * [Log configuration](https://github.com/yegor86/spring-boot-app/blob/main/src/main/resources/application.properties#L22-L32)


## Exercise 2
The implementation can be found by the link [src/main/java/com/example/task1](src/main/java/com/example/task1)

### Requirements fullfilment:
1) Small restAPI web-application
    * web app with REST API
2) all data (except files) should be in JSON format
    * Job registration API returns retuls in JSON API http://localhost:8080/api/v1/job/register
3) should have API for adding a job for file parsing (“register-job”)
    * Implemented job registration API /api/v1/job/register
4) “register-job” should return id of the job
    * Job registration API has the following format 
    ```json
    {"jobId":4,"status":"Pending"}
    ```
5) File should be parsed in asynchronous way, result should be stored in DB
    * Job registraion service register the job, which is proccessed by [ScheduledJobService](src/main/java/com/example/task1/service/ScheduledJobService.java#L23) asynchronously
6) should have API for getting result of parsed file by Job ID
    * Job Status API is implemented in [RegisterJobController](src/main/java/com/example/task1/controller/RegisterJobController.java#L68-L72)
    
    E.g.:
    ```
    $ curl http://localhost:8080/api/v1/job/status/4
    ```

7) should have API for searching results by name, code

    * GeoClass Search API is implemented in [RegisterJobController](src/main/java/com/example/task1/controller/RegisterJobController.java#L74-L81)

    E.g.:
    ```
    $ curl http://localhost:8080/api/v1/geoclass/search?name=Geo%20Class%201&code=GC2
    ```
8) Basic Authorization should be supported (optional)
9) Page for jobs adding and result view (optional)
10) Provide junit tests to test the API’s

## Unit Tests
TODO