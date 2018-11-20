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
$LocationID = $_POST['RuleID'];
$SensorID = $_POST['SensorID'];
$Operator = $_POST['Operator'];
$Value = $_POST['Value'];
$Action = $_POST['Action'];

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "UPDATE Rule SET SensorID=$SensorID, Operator=$Operator, Value=$Value, Action=$Action WHERE RuleID=$RuleID";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$conn->close();
?>
