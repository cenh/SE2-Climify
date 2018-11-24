<?php
require_once "../meta.php";

if($currentUserID == ""){
  echo '{"status":"No UserID"}';
	exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$LocationID = $_POST['LocationID'];
$sensorID = $_POST['SensorID'];
$Operator = $_POST['Operator'];
$Value = $_POST['Value'];
$Action = $_POST['Action'];
$setTemp = $_POST['setTemperature'];

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "INSERT INTO Rule(SensorID, Operator, Value, Action) VALUES ("`$sensorID`, `$Operator`, `$Value`, `$Action`)";
$result = mysqli_query($conn, $q) or die("1Error in Selecting " . mysqli_error($conn));

# Get the RuleID, so we can insert into RuleLocation
$q = "SELECT RuleID FROM Rule WHERE SensorID=$sensorID AND Operator=$Operator AND Value=$Value AND Action=$Action";
$result = mysqli_query($conn, $q) or die("2Error in Selecting " . mysqli_error($conn));

if($result->num_rows==1){
  while($row = $result->fetch_assoc()) {
    $RuleID = $row["RuleID"];
  }
}
else {
  echo '{"status":"More than one Rule was returned, only expected one"}';
  exit;
}

$q = "INSERT INTO RuleLocation VALUES ($RuleID, $LocationID)";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$conn->close();
 ?>
