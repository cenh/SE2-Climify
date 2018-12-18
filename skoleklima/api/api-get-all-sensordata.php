<?php
/*
 *	Author: Christian Hansen & KacperZyla
 */
$body = $_POST["sensor"];
switch ($body) {
    case "readOutdoorTemperature":
        $dbVariable = "Temperature";
        break;
    case "setTemperature":
        $dbVariable = "Temperature";
        break;
    case "readHumidity":
        $dbVariable = "Humidity";
        break;
    case "readBattery":
        $dbVariable = "Battery";
        break;
}

    // TODO: any more things need to be called from this api?? (Hasn't been tested)
$data = file_get_contents(   "http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20".$dbVariable ."%20FROM%20".$body);

echo $data;

return $data;
