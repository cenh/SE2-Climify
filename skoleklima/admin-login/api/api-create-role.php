<?php
//@author ciok
//create a role with a given name

require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);
$role_name = clean($_POST[role_name]);

if (!$systemAccess) {
    echo '{"status":"systemAccess error"}';
    exit;
}


if ($phaseSessionToken != $adminSessionToken) {
    echo '{"status":"phaseSessionToken error"}';
    exit;
}

$servername = DB_HOST;
$username = DB_USER;
$password = DB_PASSWORD;
$databasename = DB_NAME;
$pepper = HASH_PEPPER;

$conn = new mysqli($servername, $username, $password, $databasename);
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

$query = "SELECT MAX(RoleID) FROM Role";

$stmt = $conn->prepare($query);

$stmt->execute();

$result = $stmt->get_result();

$id = mysqli_fetch_array($result)[0];
$id = $id+1;

$q = "INSERT INTO Role(RoleID, RoleName, protected) VALUES ($id, 'test3', 0)";
mysqli_query($conn, $q) or die("Error in Inserting " . mysqli_error($conn));


$stmt->close();
$conn->close();
