<?php
$names = $_POST["sensors_names"];

$results = array();

foreach ($names as $name) {
    $data = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20LAST(*)%20FROM%20" . $name);
    array_push($results, $data);
}

echo $results;

return $results;