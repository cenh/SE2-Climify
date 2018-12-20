<?php
/*
 *	Author: Christian Hansen & Kacper Zyla
 */
$humidity = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20Humidity%20FROM%20readHumidity");
echo $humidity;
return $humidity;
