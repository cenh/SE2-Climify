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

$q = "SELECT * FROM Rule NATURAL JOIN RuleLocation WHERE RuleLocation.LocationID=$LocationID";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$emparray = array();
while($row = mysqli_fetch_assoc($result))
{
  $temparray = [];
  $sensor = $row["SensorID"];
  $q2 = "SELECT * FROM SensorType NATURAL JOIN SensorInstance WHERE SensorInstance.SensorID=\"$sensor\"";
  error_log($q2, 0);
  $result2 = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));
  while($row2 = mysqli_fetch_assoc($result2))
  {
    array_push($temparray[SensorType] = $row2["SensorTypeName"]);
  }
  array_push($temparray[RuleID] = $row["RuleID"]);
  array_push($temparray[SensorID] = $row["SensorID"]);
  array_push($temparray[Operator] = $row["Operator"]);
  array_push($temparray[Value] = $row["Value"]);
  array_push($temparray[Action] = $row["Action"]);
  array_push($emparray, $temparray);
}
echo(json_encode($emparray));

$conn->close();
 ?>
