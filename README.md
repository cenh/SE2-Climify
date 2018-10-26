# SE2-Climify:

## Releases
Tag for release #1: https://github.com/cenh/SE2-Climify/tree/R1

## communication/mqtt
Contains Java code for communication between Virtual Machine and Raspberry Pi.
The project is using Maven, and is structured with a parent project called mqtt and 3 submodules.
To build the project you run the following command on the parent project:
maven clean package 
This will create a jar for each project in the corresponding target folders which should be run on the VM and Raspberry Pi's.
At the moment it creates both a jar with and without dependencies; for it to work you should use the one with dependencies in it.


Subfolders:
Climify: runs serverside communication

Raspberry Pi: 
runs RPi communication


MqttLib: Shared code


## feedback_app
Contains code from previous projects, used for mobile application

## sensor_app
Contains code from previous projects, used for mobile application

## skoleklima
Contains the the Skoleklima system running on the Virtual Machine.  
Code changed:  
api/api-get-sensor-info.php - API to retrive data from a given sensor  
js/main.js - MQTT message sending mechanism added  
js/charts.js - Slight modifications  
index.php - Added js scripts  
meta.php - Configured it to work with our mysql database  
influx-meta.php - Configured it to work with our influx database   
api/api-get-graph-data.php - Bug fixes and modififications required for it to work with our system  
ink/views/view-roomX.php - New File with Room X page  





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

Mock user: john - password: testpassword

Mock admin: admin - password: totallysecurepassword

Mysql user: root - password: totallysecurepassword

Jenkins: admin - password: totallysecurepassword

## Release 1

The first release seeks to fulfill the user stories specified in the report. 

Currently, the first release can be accessed at http://se2-webapp03.compute.dtu.dk/playground/skoleklima/

In the navigation a tab has been added named "Room X". This is where release 1's features are demonstrated.

On the page it will be possible to set the thermostat (via MQTT) and view latest influxdb data available.

The graph used is not grafana, nor is it connected to influxdb yet, it is a chartJS clientside implementation where it displays the set temperature on the thermostat during your current session. As this is stored on the frontside in javascript it is reloaded whenever the browser reloads. 

To get data from the influxdb we make http API calls to the 8086 port on the local machine, and process the response with javascript on the clientside. 
