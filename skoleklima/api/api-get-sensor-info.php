<?php
$sensor = $_GET['sensor'];
$returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20".$sensor);
$returnFromIfx = json_encode($returnFromIfx,true);
echo $returnFromIfx;
return $returnFromIfx;
?>
