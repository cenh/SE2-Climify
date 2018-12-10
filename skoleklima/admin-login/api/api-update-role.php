<?php

require_once "../admin-meta.php";

if($currentUserID == ""){
  echo '{"status":"No UserID"}';
	exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;


$roleid = $_POST['RoleID'];
$permissions = $_POST['Permissions'];


$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "DELETE FROM RolePermission WHERE RoleID=$roleid";
mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

error_log($q,0);

for($i = 0; $i < count($permissions); $i++){
    $q = "INSERT INTO RolePermission(RoleID, PermID, InstID) VALUES ($roleid, $permissions[$i], 1)";
    mysqli_query($conn, $q) or die("Error in Inserting " . mysqli_error($conn));
    error_log($q,0);

}

$conn->close();
?>