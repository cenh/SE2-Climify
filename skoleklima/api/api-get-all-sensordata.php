<?php
$body = file_get_contents('php://input');
/*switch ($body) {
    case "readOutdoorTemperature":
        $dbVariable = "Temperature";
    case "setTemperature":
        $dbVariable = "Temperature";
    case "readHumidity":
        $dbVariable = "Humidity";
    case "readBattery":
        $dbVariable = "Battery";
}*/

    // TODO: any more things need to be called from this api?? (Hasn't been tested)
//$data = file_get_contents(   "http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20".$dbVariable ."FROM%20".$body);
echo $body;
return $body;