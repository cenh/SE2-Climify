<?php
$names = $_POST['sensors_names'];
//$names = array_unique(json_decode($_POST['sensors_names']));

$results = array();

foreach ($names as $name) {
//    $data = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20LAST(*)%20FROM%20" . $name);
    array_push($results, $name);
}

$resultsJSON = json_encode( $names , JSON_UNESCAPED_UNICODE );

echo $resultsJSON;

return $resultsJSON;