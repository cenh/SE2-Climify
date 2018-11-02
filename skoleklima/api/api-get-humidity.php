<?php
$humidity = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readHumidity");
$humidity = json_encode($humidity, true);
return $humidity;
