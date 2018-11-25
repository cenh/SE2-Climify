<?php
$body = $_POST["sensor"];

// TODO: any more things need to be called from this api?? (Hasn't been tested)
$data = file_get_contents(   "http://localhost:8086/query?u=admin&p=groupc&db=scadb&q=SELECT%20LAST(*)%20FROM%20".$body);

echo $data;

return $data;