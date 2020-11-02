# table-triggers-web-app
Inventory Management Application (Spring Boot) that Demos Table Triggers in Oracle

The triggers implement a business hours rule, inventory auditing and only allow orders to be processed if there is a sufficient quantity of the product in stock.

The Oracle Database is implemented using a Docker Container, therefore follow the ensuing steps when launching the app:
1) Create Docker Container with the following command:
docker run -dit --name oracle-db-container -p 51234:1521 -e DB_SID=IMSDB store/oracle/database-enterprise:12.2.0.1-slim

2) Connect to the database instance:
SYS AS SYSDBA 
Oradoc_db1 

3) Create the DBA:
CREATE USER C##AL IDENTIFIED BY AL;
GRANT DBA TO C##AL;

4) Set the following properties in the application.properties file (if not already set):
spring.datasource.username=C##AL
spring.datasource.password=AL
spring.flyway.enabled=true

5) Launch the app. 

6) After successful launch, connect as C##AL and create the app user account:
CREATE USER C##APP_USER IDENTIFIED BY APP;
GRANT CREATE SESSION TO C##APP_USER;
GRANT SELECT, INSERT, UPDATE, DELETE ON PRODUCTS TO C##APP_USER;
GRANT SELECT, INSERT ON AUDIT_PRODUCTS TO C##APP_USER;
GRANT SELECT, INSERT, UPDATE, DELETE ON ORDERS TO C##APP_USER;
GRANT SELECT ON C##AL.SEQ_ORDERS_ID TO C##APP_USER;
GRANT SELECT ON C##AL.SEQ_PRODUCTS_ID TO C##APP_USER;

7) Terminate app.

8) Make the following changes in the application.properties file:
spring.datasource.username=C##APP_USER
spring.datasource.password=APP
spring.flyway.enabled=false

9) Launch app.

Done!


