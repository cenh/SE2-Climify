<?php
/*
 *	Author: Christian Hansen & Kacper Zyla
 */
$sensor = $_GET['sensor'];
//$returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20value%20FROM%20".$sensor);
$returnFromIfx = file_get_contents("http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SHOW%20MEASUREMENTS");

echo $returnFromIfx;
return $returnFromIfx;
?>
