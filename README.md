# grant-disbursement

Build Environment
-----------------
1. This Java8 spring-boot project is using gradle to build.
2. If your machine does not have gradle installed, please follow
    https://gradle.org/install/
3. Command to build the project:
    ./gradlew build
4. Command to RUN the project:
    ./gradlew bootRun

5. If there is context load exception when executing ./gradlew command, it means that connection to Database is not setup yet.
    Please proceed to Datasource Section

Datasource
-----------
1. This project uses PostgreSQL
2. If your machine does not have PostgreSQL installed, please follow
    https://blog.timescale.com/tutorials/how-to-install-psql-on-mac-ubuntu-debian-windows/
3. Once setup is done, open a terminal and enter command
    psql
4. So that you are connected to postgres
5. Open the .sql files in the project path "src/resources/db.changelog"
6. Copy those .sql files content and paste into the psql terminal.

You should be all set, run the project again.
    ./gradlew bootRun

API Documentation
------------------
1. This project supports Swagger-UI doc, access the URL below when the project is running.
    http://localhost:8080/swagger-ui.html#/household-controller