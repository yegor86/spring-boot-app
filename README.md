## Run the Application

To run the application, run the following command in a terminal window from the root directory:

    ./gradlew bootRun

## Test service based app
Now run the service with curl (in a separate terminal window), by running the following
command (shown with its output):

    $ curl http://localhost:8080/

## Upload CSV file 
* Open http://localhost:8080/import URL in a web browser
* Browse for a [sample](samples/geoclasses.csv) and then click "Upload File"
* Track the status of the file being processed under http://localhost:8080/status/{fileName}

## Exercise 1
The implementation can be found by the link [src/main/java/com/example/task1](src/main/java/com/example/task1)


## Unit Tests
TODO