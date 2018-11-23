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

$q = "SELECT * FROM SensorType NATURAL JOIN SensorInstance WHERE SensorType.SensorTypeID=SensorInstance.SensorTypeID AND SensorInstance.LocationID=$LocationID";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$emparray = array();
while($row = mysqli_fetch_assoc($result))
{
  $temparray = [];
  $sensor = $row["SensorTypeID"];
  array_push($temparray[SensorTypeID] = $row["SensorTypeID"]);
  array_push($temparray[SensorTypeName] = $row["SensorTypeName"]);
  array_push($temparray[SensorID] = $row["SensorID"]);
  array_push($temparray[LocationID] = $row["LocationID"]);
  array_push($emparray, $temparray);
}
echo(json_encode($emparray));

$conn->close();

 ?>
