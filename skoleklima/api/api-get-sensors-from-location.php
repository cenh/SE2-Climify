<?php

require_once "../meta.php";

if( $currentUserID == ""){
  echo '{"status":"No UserID"}';
	exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$LocationID = $_GET['LocationID'];

$conn = new mysqli($servername, $username, $password, $databasename);
if($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "SELECT items.* FROM Items as items" .
     "INNER JOIN RaspberryPis as rp" .
     "INNER JOIN Things as t" .
     "INNER JOIN ThingsChannels as tc" .
     "INNER JOIN Links as links" .
     "WHERE rp.LocationID = $LocationID" .
     "AND t.RaspberryPiUID = rp.UID" .
     "AND tc.ThingUID = t.UID" .
     "AND links.ChannelUID = tc.ChannelUID" .
     "AND items.Name = links.ItemName" .
     "AND items.ReadOnly = 1;"
error_log($q, 0);
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$emparray = array();
while($row = mysqli_fetch_assoc($result))
{
  $temparray = [];
  array_push($temparray[SensorID] = $row["Name"]);
  array_push($temparray[SensorTypeName] = $row["Category"]);
  array_push($temparray[LocationID] = $LocationID);
  array_push($emparray, $temparray);
}
echo(json_encode($emparray));

$conn->close();

 ?>
