<?php
/*
 *	Author: Christian Hansen & Kacper Zyla
 */
require_once "../meta.php";

if($currentUserID == ""){
  echo '{"status":"No UserID"}';
	exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$RuleID = $_POST['RuleID'];
$LocationID = $_POST['LocationID'];

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "DELETE FROM Rule WHERE RuleID=$RuleID";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

$conn->close();
?>
