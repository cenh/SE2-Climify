<?php

//************************************************
//	Get influx database info
//************************************************

require_once "../meta.php";

if( $currentUserID == ""){
    echo '{"status":"error"}';
    exit;
} 

// Validate API key
$apiPassword = API_PASSWORD;
$phase_api_key = clean($_POST['fAY2YfpdKvR']);

//if( $apiPassword !== $phase_api_key){
//    echo '{"status":"error"}';
//    exit;
//}

$returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20readBattery");

$returnFromIfx = json_decode($returnFromIfx, true);
echo $returnFromIfx;


if ($returnFromIfx[results][0][series][0][name] != "measurements") {
    echo '{"status":"error"}';
    exit;
} 

echo '{"status":"ok"}';

?>
