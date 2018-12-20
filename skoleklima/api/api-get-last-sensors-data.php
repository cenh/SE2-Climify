<?php
//@author ciok
//get last measurements for a list of sensors from inlux

$names = $_POST['sensors_names'];

$results = array();

foreach ($names as $name) {
    $data = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20LAST(*)%20FROM%20" . $name);
    array_push($results, $data);
}

$resultsJSON = json_encode( $results , JSON_UNESCAPED_UNICODE );

echo $resultsJSON;

return $resultsJSON;