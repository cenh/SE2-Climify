# SE2-Climify:

## Releases

### Release 2
Tag for release #1: https://github.com/cenh/SE2-Climify/tree/R2

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

#### Communication files changed
Here is a list of the files changed and their changes within the 'communication/mqtt' folder.

1. 

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
