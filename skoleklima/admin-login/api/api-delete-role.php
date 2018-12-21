<?php
//@author ciok
//delete a role with a given id

require_once "../admin-meta.php";
require_once "../session.php";

$phaseSessionToken = clean($_POST[sessionToken]);
$role_id = clean($_POST[role_id]);

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

$q = "DELETE FROM Role WHERE Role.RoleID = $role_id";
mysqli_query($conn, $q) or die("Error in Inserting " . mysqli_error($conn));

$conn->close();
