# SE2-Climify:
For a list of mock-users and their corresponding passwords, together with the IP addresses and other system accounts for the VMs, look at the bottom of this file.

## Installation
A short and quick installation guide for Climify.

### Jenkins server
Prerequistive:
1. Java
2. Jenkins

Follow the Jenkins installation guide, set up a user account.

### Webserver
Prerequistive:
1. Java
2. PHP
3. Apache

Make sure that Apache is set up correctly. Then place the skoleklima files inside /var/www/html/, then run the Climify.jar (See below).

### Communication
Prerequistive:
1. Maven
2. Java

Go to communication/mqtt and enter command:
'mvn clean install package'

This will install the different .jar for each of the communication components. E.g. the Climify webserver .jar will be located in Climify/target. This can be run using 'java -jar Climify.jar'

## Releases
A list of releases in descending order.

### Release 3
Tag for release #3: https://github.com/cenh/SE2-Climify/tree/R3

#### Skoleklima files changed:
Since release 2, the list of files changed in 'skoleklima' folder

1. css/styles.css - Edit and delete rules implementation called for updated styling
2. js/api-getter.js - removed some prints so web console is cleaner
3. js/charts.js - fixed some bugs, and added functionality to calculate statistics. Also made it such that the download .csv for graphs work
4. js/devices.js - created this file for device page
5. js/main.js - moved some functions to devices.js
6. js/rule-getter.js - functions that allow CRUD operations on rules
7. ink/navigation.php - added new route 'control map'
8. ink/views/view-control-map.php - new route
9. ink/views/view-data-map.php - added the working devices page
10. ink/views/view-dashboard.php - added the new route 'control' map to appropriate permissions, changed how permissions are used
11. ink/views/view-data.php - added a mouseover that shows reccomended indicator ranges
12. ink/views/view-rules.php - added dynamic elements that allow CRUD operations for rules
13. api/api-get-rules.php - added new API for getting rules
14. api/api-rule-delete.php - added new API for deleting rules
15. api/api-rule-execute.php - preliminary API for executing rules (No longer used)
16. api/api-rule-save.php - added new API for saving rules
17. api/api-rule-update.php - added new API for updating rules

#### Communication (Java) files changed
Here is a list of the files changed and their changes within the 'communication/mqtt' folder.

1. Climify/src/main/java/org/Climify/ClimifyMessageHandler.java - Now supports CRUD operations with rules and executes them
2. Climify/src/main/java/org/Climify/ClimifyMQTTController.java - Handles MQTT messages
3. Climify/src/main/java/org/Climify/mariaDB/MariaDBCommunicator.java - Now gets rules from MariaDB
4. MqttLib/src/main/java/org/MqttLib/openhab/Item.java - now contains item description as well.
5. MqttLib/src/main/java/org/MqttLib/openhab/Option.java - created file for openHAB options
6. MqttLib/src/main/java/org/MqttLib/openhab/StateDescription.java - defines the description of an item
7. RaspberryPi/src/main/java/org/RaspberryPi/openhab/RestCommunicator.java - now looks for state description as well

### Release 2
Tag for release #2: https://github.com/cenh/SE2-Climify/tree/R2

#### Skoleklima files changed:
Here is a list of the files changed and their changes within the 'skoleklima' folder.

1. ink/views/view-data-map.php & ink/views/view-data-map.php - changes to reflect new tables.
2. ink/navigation.php - changes to reflect permissions.
3. js/charts.js - changed to retrieve data from more sensors.
4. api/api-get-all-sensordata.php - Changed to get all sensor data.
5. api/api-user-login.php - Updated the security.
6. api/api-get-current-temperature.php, api/api-get-current-humidty.php & api/api-get-battery-level.php - Created to retrieve sensor data.
7. api/api-get-graph-data.php - Changed to support multiple sensors.
8. admin-login/api/api-admin-login.php - Updated the security.
9. admin-login/api/api-get-roles.php - Added role tables to the admin page.
10. admin-login/index.php - Added role table.

#### Communication (Java) files changed
Here is a list of the files changed and their changes within the 'communication/mqtt' folder.

1. Climify/src/main/java/org/Climify/ClimifyMessageHandler.java - Changed to also save sensor in MariaDB.
2. Climify/src/main/java/org/Climify/ClimifyMqttController.java - MariaDB connection.
3. Climify/src/main/java/org/Climify/influxDB/InfluxCommunicator.java - Updated to save category aswell.
4. Climify/src/main/java/org/Climify/mariaDB/MariaDBCommunicator.java - MariaDB communication protocol.
5. MqttLib/src/main/java/org/MqttLib/openhab/ - Various updates and new files for openHAB usage.
6. RaspberryPi/src/main/java/org/RaspberryPi/ - Various changes to RaspberryPi communication with MQTT.

### Release 1
Tag for release #3: https://github.com/cenh/SE2-Climify/tree/R1

The first release seeks to fulfill the user stories specified in the report.

Currently, the first release can be accessed at http://se2-webapp03.compute.dtu.dk/playground/skoleklima/

In the navigation a tab has been added named "Room X". This is where release 1's features are demonstrated.

On the page it will be possible to set the thermostat (via MQTT) and view latest influxdb data available.

The graph used is not grafana, nor is it connected to influxdb yet, it is a chartJS clientside implementation where it displays the set temperature on the thermostat during your current session. As this is stored on the frontside in javascript it is reloaded whenever the browser reloads.

To get data from the influxdb we make http API calls to the 8086 port on the local machine, and process the response with javascript on the clientside.

An example:

```php

```

# VMs

Information about the VMs

## Jenkins server
Domain: se2-jenkins03.compute.dtu.dk

IP: 130.225.68.155

Jenkins webservice: http://se2-jenkins03.compute.dtu.dk:8080/

User: admin - password: totallysecurepassword

## Webapp server
Domain: se2-webapp03.compute.dtu.dk

IP: 130.225.69.76

Webapp: http://se2-webapp03.compute.dtu.dk/SE2-Climify/skoleklima/index.php

Admin login: http://se2-webapp03.compute.dtu.dk/SE2-Climify/skoleklima/admin-login/index.php

Jenkins: http://130.225.69.76:8080/

Mock user (Role: Project Manager): john - password: testpassword

Mock user (Role: Project Observer): jason - password: testpassword

Mock admin: admin - password: totallysecurepassword (Login via the Admin login page, see above link)

Mysql user: root - password: totallysecurepassword

Jenkins: admin - password: totallysecurepassword
