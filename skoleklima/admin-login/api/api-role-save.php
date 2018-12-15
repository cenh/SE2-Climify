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

$rolename = $_POST['RoleName'];
$permissions = $_POST['Permissions'];

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
  echo '{"status":"Connection died"}';
  die("Connection error: " . $conn->connect_error);
  exit;
}

$q = "SELECT RoleID FROM Role ORDER BY RoleID DESC LIMIT 1";
$result = mysqli_query($conn, $q) or die("Error in Selecting " . mysqli_error($conn));

if($result->num_rows==1){
  while($row = $result->fetch_assoc()) {
    $roleid = $row["RoleID"];
  }
}
else {
  echo '{"status":"More than one Role was returned, only expected one"}';
  exit;
}

$roleid += 1;

$q = "INSERT INTO Role(RoleID, RoleName) VALUES ($roleid,'$rolename')";
mysqli_query($conn, $q) or die("Error in Inserting " . mysqli_error($conn));


for($i = 0; $i < count($permissions); $i++){
    $q = "INSERT INTO RolePermission(RoleID, PermID, InstID) VALUES ($roleid, $permissions[$i], 1)";
    mysqli_query($conn, $q) or die("Error in Inserting " . mysqli_error($conn));
}

$conn->close();
?>